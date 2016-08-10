package org.devoware.example.config;

import javax.inject.Singleton;
import javax.validation.Validator;

import org.devoware.config.db.DataSourceFactory;
import org.devoware.homonculus.setup.Environment;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.health.HealthCheckRegistry;
import com.fasterxml.jackson.databind.ObjectMapper;

import dagger.Module;
import dagger.Provides;

@Module
public class ConfigurationModule {

  private Dagger2SampleConfiguration config;
  private Environment environment;

  public ConfigurationModule(Dagger2SampleConfiguration config, Environment environment) {
    this.config = config;
    this.environment = environment;
  }

  @Provides
  @Singleton
  public Dagger2SampleConfiguration provideConfiguration() {
    return config;
  }

  @Provides
  @Singleton
  public DataSourceFactory provideDataSourceFactory() {
    return config.getDataSourceFactory();
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
