package com.doradosystems.mis.scheduler.scheduling.job;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.inject.Singleton;

import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.spi.JobFactory;
import org.quartz.spi.TriggerFiredBundle;

import com.doradosystems.mis.scheduler.config.JobConfiguration;
import com.doradosystems.mis.scheduler.scheduling.SchedulerCreationException;
import com.google.common.collect.Maps;

import dagger.Module;
import dagger.Provides;

@Module
public class JobModule {
  
  @Provides
  @Singleton
  @Nonnull
  public JobFactory provideJobFactory(@Nonnull final DdeCrawlerJob ddeCrawlerJob) {
    return new JobFactory() {
      @Override
      public Job newJob(TriggerFiredBundle bundle, Scheduler scheduler) throws SchedulerException {
        switch (bundle.getJobDetail().getKey().getName()) {
          case "ddeCrawler":
          default:  
              return ddeCrawlerJob;
        }
      }
    };
  }

  @Provides
  @Singleton
  @Nonnull
  public Map<String, Trigger> provideJobTriggers(@Nonnull List<JobConfiguration> jobConfigs) {
    final Map<String, Trigger> triggersByJobName = Maps.newHashMap();
    jobConfigs.forEach((config) -> {
      triggersByJobName.put(config.getName(), newTrigger()
          .withIdentity(config.getName())
          .withSchedule(cronSchedule(config.getRecurrenceRule()))
          .forJob(new JobKey(config.getName()))
          .build());
    });
    return triggersByJobName;
  }


  @Provides
  @Singleton
  @Nonnull
  public InitializationStatus provideInitializationStatus(@Nonnull Scheduler scheduler,
     @Nonnull Map<String,Trigger> triggersByJobName) {
    JobDetail jobDetail = newJob(DdeCrawlerJob.class).withIdentity("ddeCrawler").build();
    
    try {
      Trigger trigger = triggersByJobName.get("ddeCrawler");
      if (trigger == null) {
        throw new SchedulerCreationException("The ddeCrawler job is not configured.");
      }
      scheduler.scheduleJob(jobDetail, trigger);
    } catch (SchedulerException e) {
      throw new SchedulerCreationException(e);
    } 
    return InitializationStatus.COMPLETED;
  }


  public static enum InitializationStatus {
    COMPLETED, FAILED
  }
}
