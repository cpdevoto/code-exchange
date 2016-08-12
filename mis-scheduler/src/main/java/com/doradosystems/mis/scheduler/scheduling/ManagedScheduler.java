package com.doradosystems.mis.scheduler.scheduling;

import static java.util.Objects.requireNonNull;

import javax.annotation.Nonnull;

import org.devoware.homonculus.lifecycle.Managed;
import org.quartz.Scheduler;

public class ManagedScheduler implements Managed {
  
  private final Scheduler scheduler;

  public ManagedScheduler(@Nonnull Scheduler scheduler) {
    requireNonNull(scheduler);
    this.scheduler = scheduler;
  }
  
  @Override
  public void start() throws Exception {
    scheduler.start();
  }
  
  @Override
  public void stop() throws Exception {
    scheduler.shutdown();
  }
  

}
