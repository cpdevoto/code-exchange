package com.doradosystems.mis.worker;

import org.devoware.homonculus.core.Application;
import org.devoware.homonculus.core.lifecycle.Managed;
import org.devoware.homonculus.core.setup.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.doradosystems.mis.worker.config.ApplicationConfiguration;
import com.doradosystems.mis.worker.config.ConfigurationModule;
import com.doradosystems.mis.worker.setup.EnvironmentModule;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.util.StatusPrinter;

public class MisWorkerApplication extends Application<ApplicationConfiguration> {

  private static final int DEFAULT_TERMINATION_PORT = 51435;

  private static final Logger log = LoggerFactory.getLogger(MisWorkerApplication.class);
  
  private MisWorkerComponent component;
  
  @Override
  public String getName() {
    return "mis-worker";
  }
  
  @Override
  public int getTerminationPort() {
    return DEFAULT_TERMINATION_PORT;
  }

  @Override
  protected void initialize(ApplicationConfiguration config, Environment env) {
    try {
      //Instantiate the Dagger component, which will generate the object graph, wiring everything together
      component = DaggerMisWorkerComponent.builder()
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
    } catch (Throwable t) {
      log.error("A problem occurred during application initialization", t);
      throw t;
    }
  }
}
