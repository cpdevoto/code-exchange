package com.doradosystems.mis.scheduler;

import org.devoware.homonculus.Application;
import org.devoware.homonculus.setup.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.doradosystems.mis.scheduler.config.ConfigurationModule;
import com.doradosystems.mis.scheduler.config.MisSchedulerConfiguration;
import com.doradosystems.mis.scheduler.setup.EnvironmentModule;

public class MisSchedulerApplication extends Application<MisSchedulerConfiguration> {

  @SuppressWarnings("unused")
  private static final Logger log = LoggerFactory.getLogger(MisSchedulerApplication.class);
  
  private MisSchedulerComponent component;
  
  @Override
  public String getName() {
    return "mis-scheduler";
  }

  @Override
  protected void initialize(MisSchedulerConfiguration config, Environment env) {
    //Instantiate the Dagger component, which will generate the object graph, wiring everything together
    component = DaggerMisSchedulerComponent.builder()
        .configurationModule(new ConfigurationModule(config))
        .environmentModule(new EnvironmentModule(env))
        .build();
    System.out.println(component.getDataSource().getClass().getName());
  }
}
