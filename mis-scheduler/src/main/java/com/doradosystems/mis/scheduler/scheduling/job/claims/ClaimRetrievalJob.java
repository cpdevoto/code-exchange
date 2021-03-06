package com.doradosystems.mis.scheduler.scheduling.job.claims;

import static com.codahale.metrics.MetricRegistry.name;
import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import org.devoware.homonculus.messaging.output.OutputChannel;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codahale.metrics.Gauge;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;
import com.doradosystems.mis.agent.model.ClaimRetrievalTask;
import com.doradosystems.mis.agent.model.NationalProviderIdentifier;
import com.doradosystems.mis.agent.model.Operator;
import com.doradosystems.mis.agent.model.OperatorNpi;
import com.doradosystems.mis.agent.model.messaging.MessageHeaders;
import com.doradosystems.mis.agent.model.messaging.MessageType;
import com.doradosystems.mis.scheduler.dao.NpiDao;
import com.doradosystems.mis.scheduler.dao.OperatorDao;
import com.doradosystems.mis.scheduler.dao.OperatorNpiDao;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;

public class ClaimRetrievalJob implements Job {
  private static final Logger log = LoggerFactory.getLogger(ClaimRetrievalJob.class);

  // DateFormat is not threadsafe, so we create one per thread using ThreadLocal
  private static final ThreadLocal<SimpleDateFormat> formatter =
      new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
          return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX");
        }
      };


  private final OperatorDao operatorDao;
  private final NpiDao npiDao;
  private final OperatorNpiDao operatorNpiDao;
  private final OutputChannel outputChannel;
  private final ObjectMapper mapper;
  private final Timer jobExecutionTimes;

  private volatile String lastExecutionTime = "Not yet executed";
  private volatile int tasksGenerated = 0;



  @Inject
  public ClaimRetrievalJob(@Nonnull MetricRegistry metrics, @Nonnull OperatorDao operatorDao,
      @Nonnull NpiDao npiDao, @Nonnull OperatorNpiDao operatorNpiDao,
      @Nonnull OutputChannel outputChannel, @Nonnull ObjectMapper mapper) {
    this.operatorDao = requireNonNull(operatorDao);
    this.npiDao = requireNonNull(npiDao);
    this.operatorNpiDao = requireNonNull(operatorNpiDao);
    this.outputChannel = requireNonNull(outputChannel);
    this.mapper = requireNonNull(mapper);

    this.jobExecutionTimes = metrics.timer(name(ClaimRetrievalJob.class, "jobExecutionsTimes"));

    metrics.register(name(ClaimRetrievalJob.class, "lastExecutionTime"), new Gauge<String>() {
      @Override
      public String getValue() {
        return lastExecutionTime;
      }
    });

    metrics.register(name(ClaimRetrievalJob.class, "tasksGenerated"), new Gauge<Integer>() {
      @Override
      public Integer getValue() {
        return tasksGenerated;
      }
    });

  }

  @Override
  public void execute(@Nonnull JobExecutionContext context) throws JobExecutionException {
    this.lastExecutionTime = format(System.currentTimeMillis());
    Timer.Context timer = jobExecutionTimes.time();
    log.info("Starting the Scheduled Claim Retrieval Job...");
    try {
      List<ClaimRetrievalTask> tasks = crawl();
      publishTasks(tasks);
      this.tasksGenerated = tasks.size();
      log.info("The Scheduled Claim Retrieval Job was completed successfully.");
    } catch (Throwable t) {
      log.error("A problem occurred while executing the Scheduler Claim Retrieval Job", t);
      throw new JobExecutionException(t);
    } finally {
      timer.stop();
    }

  }

  @Nonnull
  private List<ClaimRetrievalTask> crawl() throws Throwable {
    // We use completable futures to retrieve the list of operators, the list of npis, and the list
    // of operator npis in separate threads, and then push the results of these operations into the getTasks
    // method.
    // TODO: If the the system grows to include many thousands of operators and npis, this approach will not scale, since it requires 
    // that all operators and npis be loaded into memory. A better approach would be to create an operator_npi_view in the database and
    // perform a series of paginated queries to only load a small subset of the data into memory at a time. I decided it wasn't worth
    // doing for a POC.
    try {
      return getOperators().thenCombineAsync(getNationalProviderIdentifiers(), this::generateLookups)
          .thenCombineAsync(getOperatorNpis(), this::getTasks)  
          .get();
    } catch (InterruptedException e) {
      throw e;
    } catch (ExecutionException e) {
      throw e.getCause();
    }
  }

  private void publishTasks(@Nonnull List<ClaimRetrievalTask> tasks) {
    requireNonNull(tasks).forEach((task) -> {
      try {
        String json = mapper.writer().writeValueAsString(task);
        try {
          outputChannel.write(json,
              ImmutableMap.of(MessageHeaders.MESSAGE_TYPE_NAME, MessageType.CLAIM_RETRIEVAL));
        } catch (IOException ex) {
          log.error("A problem occurred while attempting to write the task to the output channel: "
              + json, ex);
        }
      } catch (JsonProcessingException ex) {
        log.error("A problem occurred while converting a task to JSON.", ex);
      }
    });
  }

  @Nonnull
  private CompletableFuture<List<Operator>> getOperators() {
    return CompletableFuture.supplyAsync(() -> operatorDao.getAll());
  }

  @Nonnull
  private CompletableFuture<List<NationalProviderIdentifier>> getNationalProviderIdentifiers() {
    return CompletableFuture.supplyAsync(() -> npiDao.getAll());
  }

  @Nonnull
  private CompletableFuture<List<OperatorNpi>> getOperatorNpis() {
    return CompletableFuture.supplyAsync(() -> operatorNpiDao.getAll());
  }

  @Nonnull
  private Lookups generateLookups(@Nonnull List<Operator> operators,
      @Nonnull List<NationalProviderIdentifier> npis) {
    return new Lookups(operators, npis);
  }

  @Nonnull
  private List<ClaimRetrievalTask> getTasks(final Lookups lookups,
      final List<OperatorNpi> operatorNpis) {
    final Multimap<Operator, NationalProviderIdentifier> operatorNationalProviderIdentifiers =
        getOperatorNpiMap(lookups, operatorNpis);
    final List<ClaimRetrievalTask> tasks = Lists.newArrayList();
    operatorNationalProviderIdentifiers.keySet().forEach((operator) -> {
      ClaimRetrievalTask task = ClaimRetrievalTask.builder().withOperator(operator)
          .withNationalProviderIdentifiers(operatorNationalProviderIdentifiers.get(operator))
          .build();
      tasks.add(task);
    });
    return tasks;
  }

  @Nonnull
  private Multimap<Operator, NationalProviderIdentifier> getOperatorNpiMap(
      @Nonnull final Lookups lookups, final List<OperatorNpi> operatorNpis) {
    final Multimap<Operator, NationalProviderIdentifier> operatorNationalProviderIdentifiers =
        ArrayListMultimap.create();
    operatorNpis.forEach((operatorNpi) -> {
      Operator operator = lookups.operators.get(operatorNpi.getOperatorId());
      NationalProviderIdentifier npi = lookups.npis.get(operatorNpi.getNpiId());
      operatorNationalProviderIdentifiers.put(operator, npi);
    });
    return operatorNationalProviderIdentifiers;
  }

  @Nonnull
  private Map<Long, Operator> getOperatorsById(@Nonnull final List<Operator> operators) {
    final Map<Long, Operator> operatorsById = Maps.newHashMap();
    operators.forEach((operator) -> {
      operatorsById.put(operator.getId(), operator);
    });
    return operatorsById;
  }

  @Nonnull
  private String format(long millis) {
    return formatter.get().format(new Date(millis));
  }

  private static class Lookups {
    private final Map<Long, Operator> operators = Maps.newHashMap();
    private final Map<Long, NationalProviderIdentifier> npis = Maps.newHashMap();

    private Lookups(@Nonnull List<Operator> operators,
        @Nonnull List<NationalProviderIdentifier> npis) {
      for (Operator operator : requireNonNull(operators)) {
        this.operators.put(operator.getId(), operator);
      }
      for (NationalProviderIdentifier npi : requireNonNull(npis)) {
        this.npis.put(npi.getId(), npi);
      }
    }
  }
}
