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
import com.doradosystems.mis.scheduler.scheduling.job.claims.ClaimRetrievalJobModule;
import com.google.common.collect.Maps;

import dagger.Module;
import dagger.Provides;

@Module(includes=ClaimRetrievalJobModule.class)
public class JobModule {
  
  @Provides
  @Singleton
  @Nonnull
  public JobFactory provideJobFactory(@Nonnull final Map<String, Job> jobs) {
    return new JobFactory() {
      @Override
      public Job newJob(TriggerFiredBundle bundle, Scheduler scheduler) throws SchedulerException {
        Job job = jobs.get(bundle.getJobDetail().getKey().getName());
        return job;
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
     @Nonnull Map<String, Class<? extends Job>> jobClassesByJobName, @Nonnull Map<String,Trigger> triggersByJobName) {
    for (String jobName : triggersByJobName.keySet()) {
      Class<? extends Job> jobClass = jobClassesByJobName.get(jobName);
      if (jobClass == null) {
        throw new JobInitializationException("No job class mapping defined for the job named " + jobName);
      }
      JobDetail jobDetail = newJob(jobClass).withIdentity(jobName).build();
      Trigger trigger = triggersByJobName.get(jobName);
      if (trigger == null) {
        throw new JobInitializationException("A trigger should exist for the job named " + jobName);
      }
      try {
        scheduler.scheduleJob(jobDetail, trigger);
      } catch (SchedulerException e) {
        throw new JobInitializationException(e);
      }
    }
    return InitializationStatus.COMPLETED;
  }


  public static enum InitializationStatus {
    COMPLETED, FAILED
  }
}
