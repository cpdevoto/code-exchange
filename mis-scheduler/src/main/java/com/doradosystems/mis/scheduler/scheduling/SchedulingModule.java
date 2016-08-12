package com.doradosystems.mis.scheduler.scheduling;

import javax.annotation.Nonnull;
import javax.inject.Singleton;

import org.devoware.homonculus.setup.Environment;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.spi.JobFactory;

import dagger.Module;
import dagger.Provides;

@Module
public class SchedulingModule {
  
  @Provides
  @Singleton
  @Nonnull
  public Scheduler provideScheduler(@Nonnull Environment env, @Nonnull JobFactory jobFactory) {
    try {
    SchedulerFactory schedulerFactory = new StdSchedulerFactory();
    Scheduler scheduler = schedulerFactory.getScheduler();
    scheduler.setJobFactory(jobFactory);
    env.manage(new ManagedScheduler(scheduler));
    return scheduler;
    } catch (SchedulerException ex) {
      throw new SchedulerCreationException(ex);
    }
  }
  
}
