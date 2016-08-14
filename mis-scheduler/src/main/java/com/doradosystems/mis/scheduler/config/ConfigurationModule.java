package com.doradosystems.mis.scheduler.config;

import java.util.List;

import javax.annotation.Nonnull;
import javax.inject.Singleton;

import org.devoware.config.db.DataSourceFactory;

import dagger.Module;
import dagger.Provides;

@Module
public class ConfigurationModule {

  private MisSchedulerConfiguration config;

  public ConfigurationModule(MisSchedulerConfiguration config) {
    this.config = config;
  }

  @Provides
  @Singleton
  @Nonnull
  public MisSchedulerConfiguration provideConfiguration() {
    return config;
  }

  @Provides
  @Singleton
  @Nonnull
  public List<JobConfiguration> provideJobConfigurations() {
    return config.getJobs();
  }

  @Provides
  @Singleton
  @Nonnull
  public DataSourceFactory provideDataSourceFactory() {
    return config.getDataSourceFactory();
  }

  @Provides
  @Singleton
  @Nonnull
  public RabbitMqConfiguration provideRabbitMqConfiguration() {
    return config.getRabbitMq();
  }
  
}
