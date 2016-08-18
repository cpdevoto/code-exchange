package com.doradosystems.mis.worker.config;

import javax.annotation.Nonnull;
import javax.inject.Singleton;

import org.devoware.config.db.DataSourceFactory;

import com.doradosystem.messaging.config.RabbitMqConfiguration;

import dagger.Module;
import dagger.Provides;

@Module
public class ConfigurationModule {

  private ApplicationConfiguration config;

  public ConfigurationModule(ApplicationConfiguration config) {
    this.config = config;
  }

  @Provides
  @Singleton
  @Nonnull
  public ApplicationConfiguration provideConfiguration() {
    return config;
  }

  @Provides
  @Singleton
  @Nonnull
  public WorkerConfiguration provideWorkerConfiguration() {
    return config.getWorker();
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
