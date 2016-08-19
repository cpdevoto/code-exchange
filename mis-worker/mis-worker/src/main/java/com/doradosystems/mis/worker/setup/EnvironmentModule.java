package com.doradosystems.mis.worker.setup;

import static com.doradosystems.mis.worker.WorkerQualifiers.JOB_QUEUE;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;

import javax.annotation.Nonnull;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.validation.Validator;

import org.devoware.homonculus.setup.Environment;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.health.HealthCheckRegistry;
import com.doradosystems.mis.worker.config.WorkerConfiguration;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

import dagger.Module;
import dagger.Provides;

@Module
public class EnvironmentModule {

  private Environment environment;

  public EnvironmentModule(Environment environment) {
    this.environment = environment;
  }

  @Provides
  @Singleton
  public Environment provideEnvironment() {
    return environment;
  }
  
  @Provides
  @Singleton
  public MetricRegistry provideMetricRegistry() {
    return environment.metrics();
  }
  
  @Provides
  @Singleton
  public HealthCheckRegistry provideHealthCheckRegistry() {
    return environment.healthChecks();
  }
  
  @Provides
  @Singleton
  public ObjectMapper provideObjectMapper() {
    return environment.getObjectMapper();
  }
  
  @Provides
  @Singleton
  public Validator provideValidator() {
    return environment.getValidator();
  }
  
  @Provides @Named(JOB_QUEUE)
  @Singleton
  @Nonnull
  public BlockingQueue<Runnable> provideJobQueue(WorkerConfiguration config) {
    return new LinkedBlockingQueue<>(config.getMaxJobsInQueue());
  }
  
  @Provides
  @Singleton
  public ExecutorService provideExecutorService(WorkerConfiguration config, @Named(JOB_QUEUE) BlockingQueue<Runnable> jobQueue) {
    final String threadName = "MIS Worker Thread";
    final String threadNamePattern = threadName + "-%d";
    
    final ThreadFactory factory = new ThreadFactoryBuilder()
        .setDaemon(false)
        .setNameFormat(threadNamePattern)
        .build();
    
    final ExecutorService executorService = environment.executorService("MIS Worker Service", factory)
        .shutdownTime(config.getShutdownTime())
        .minThreads(config.getMinThreads())
        .maxThreads(config.getMaxThreads())
        .keepAliveTime(config.getKeepAliveTime())
        .workQueue(jobQueue)
        .build();
    return executorService;
  }
}
