package org.devoware.example.config;

import javax.inject.Singleton;
import javax.sql.DataSource;
import javax.validation.Validator;

import org.devoware.example.config.db.ManagedDataSource;
import org.devoware.example.setup.Environment;

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
  public DataSource provideDataSource() {
    ManagedDataSource dataSource = config.getDataSourceFactory().build(environment.metrics(), "<default>");
    environment.manage(dataSource);
    return dataSource;
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
