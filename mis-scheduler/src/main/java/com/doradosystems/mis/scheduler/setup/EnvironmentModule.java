package com.doradosystems.mis.scheduler.setup;

import javax.inject.Singleton;
import javax.validation.Validator;

import org.devoware.homonculus.core.setup.Environment;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.health.HealthCheckRegistry;
import com.fasterxml.jackson.databind.ObjectMapper;

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
}
