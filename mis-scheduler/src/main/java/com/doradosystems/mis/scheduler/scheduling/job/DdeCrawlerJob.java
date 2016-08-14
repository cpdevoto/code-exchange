package com.doradosystems.mis.scheduler.scheduling.job;

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

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codahale.metrics.Gauge;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;
import com.doradosystems.mis.agent.model.DdeCrawlerTask;
import com.doradosystems.mis.agent.model.Npi;
import com.doradosystems.mis.agent.model.Operator;
import com.doradosystems.mis.scheduler.dao.NpiDao;
import com.doradosystems.mis.scheduler.dao.OperatorDao;
import com.doradosystems.mis.scheduler.output.OutputChannel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;

public class DdeCrawlerJob implements Job{
  private static final Logger log = LoggerFactory.getLogger(DdeCrawlerJob.class);

  private static final ThreadLocal<SimpleDateFormat> formatter =
      new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
          return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX");
        }
      };


  private final OperatorDao operatorDao;
  private final NpiDao npiDao;
  private final OutputChannel outputChannel;
  private final ObjectMapper mapper;
  private final Timer jobExecutionTimes;
  
  private volatile String lastExecutionTime = "Not yet executed";
  private volatile int tasksGenerated = 0;




  @Inject
  public DdeCrawlerJob(@Nonnull MetricRegistry metrics, @Nonnull OperatorDao operatorDao,
      @Nonnull NpiDao npiDao, @Nonnull OutputChannel outputChannel, @Nonnull ObjectMapper mapper) {
    this.operatorDao = requireNonNull(operatorDao);
    this.npiDao = requireNonNull(npiDao);
    this.outputChannel = requireNonNull(outputChannel);
    this.mapper = requireNonNull(mapper);
    
    this.jobExecutionTimes = metrics.timer(name(DdeCrawlerJob.class, "jobExecutionsTimes"));
    
    metrics.register(name(DdeCrawlerJob.class, "lastExecutionTime"), new Gauge<String>() {
      @Override
      public String getValue() {
        return lastExecutionTime;
      }
    });

    metrics.register(name(DdeCrawlerJob.class, "tasksGenerated"), new Gauge<Integer>() {
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
    log.info("Starting the DDE Crawler Job...");
    try {
      List<DdeCrawlerTask> tasks = crawl();
      publishTasks(tasks);
      this.tasksGenerated = tasks.size();
      log.info("The DDE Crawler Job was completed successfully.");
    } catch (Throwable t) {
      log.error("A problem occurred while executing the DDE Crawler Job", t);
      throw new JobExecutionException(t);
    } finally {
      timer.stop();
    }

  }

  @Nonnull
  private List<DdeCrawlerTask> crawl() throws Throwable {
    // We use completable futures to retrieve the list of operators and the list of npis
    // in separate threads, and then push the results of both operations into the getTasks
    // method.
    try {
      return getOperators().thenCombineAsync(getNpis(), this::getTasks).get();
    } catch (InterruptedException e) {
      throw e;
    } catch (ExecutionException e) {
      throw e.getCause();
    }
  }
 
  private void publishTasks(@Nonnull List<DdeCrawlerTask> tasks) {
    requireNonNull(tasks).forEach((task) -> {
      try {
        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(task);
        try {
          outputChannel.write(json);
        } catch (IOException ex) {
          log.error("A problem occurred while attempting to write the task to the output channel: " + json, ex);
        }
      } catch (JsonProcessingException ex) {
        log.error("A problem occurred while converting a task to JSON.", ex);
      }
    });
  }

  @Nonnull
  private CompletableFuture<List<Operator>> getOperators() {
    return CompletableFuture.supplyAsync(() -> operatorDao.getOperators());
  }
  
  @Nonnull
  private CompletableFuture<List<Npi>> getNpis() {
    return CompletableFuture.supplyAsync(() -> npiDao.getNpis());
  }
  

  private List<DdeCrawlerTask> getTasks(final List<Operator> operators, final List<Npi> npis) {
    final Multimap<Operator, Npi> operatorNpis = getOperatorNpis(operators, npis);
    final List<DdeCrawlerTask> tasks = Lists.newArrayList();
    operatorNpis.keySet().forEach((operator) -> {
      DdeCrawlerTask task = DdeCrawlerTask.builder().withOperator(operator)
          .withNpis(operatorNpis.get(operator)).build();
      tasks.add(task);
    });
    return tasks;
  }

  @Nonnull
  private Multimap<Operator, Npi> getOperatorNpis(@Nonnull final List<Operator> operators,
      final List<Npi> npis) {
    final Map<Long, Operator> operatorsById = getOperatorsById(operators);
    final Multimap<Operator, Npi> operatorNpis = ArrayListMultimap.create();
    npis.forEach((npi) -> {
      Operator operator = operatorsById.get(npi.getOperatorId());
      operatorNpis.put(operator, npi);
    });
    return operatorNpis;
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
  
}
