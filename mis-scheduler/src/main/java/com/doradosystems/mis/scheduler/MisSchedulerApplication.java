package com.doradosystems.mis.scheduler;

import org.devoware.homonculus.Application;
import org.devoware.homonculus.lifecycle.Managed;
import org.devoware.homonculus.setup.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.doradosystems.mis.scheduler.config.ConfigurationModule;
import com.doradosystems.mis.scheduler.config.MisSchedulerConfiguration;
import com.doradosystems.mis.scheduler.setup.EnvironmentModule;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.util.StatusPrinter;

public class MisSchedulerApplication extends Application<MisSchedulerConfiguration> {

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
    component.initialize();
    StringBuilder buf = new StringBuilder("The following resources are being managed by this application:");
    for (Managed managed: env.getManagedResources()) {
      buf.append("\n\t").append(managed.getClass().getName());
    }
    log.info(buf.toString());
    
    LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
    StatusPrinter.print(lc);
  }
}
