package com.doradosystems.mis.scheduler.scheduling.job;

import static com.codahale.metrics.MetricRegistry.name;
import static java.util.Objects.requireNonNull;

import java.text.SimpleDateFormat;
import java.util.Date;

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
import com.doradosystems.mis.scheduler.dao.NpiDao;
import com.doradosystems.mis.scheduler.dao.OperatorDao;

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
  private final Timer jobExecutionTimes;
  
  private volatile String lastExecutionTime = "Not yet executed";
  private volatile int tasksGenerated = 0;


  @Inject
  public DdeCrawlerJob(@Nonnull MetricRegistry metrics, @Nonnull OperatorDao operatorDao,
      @Nonnull NpiDao npiDao) {
    this.operatorDao = requireNonNull(operatorDao);
    this.npiDao = requireNonNull(npiDao);
    
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
      //TODO: add the main job logic here!
      log.info("The DDE Crawler Job was completed successfully.");
    } catch (Throwable t) {
      log.error("A problem occurred while executing the DDE Crawler Job", t);
      throw new JobExecutionException(t);
    } finally {
      timer.stop();
    }

  }

  public String format(long millis) {
      return formatter.get().format(new Date(millis));
  }
  
}
