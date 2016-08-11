package com.doradosystems.mis.scheduler.config;

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
  public MisSchedulerConfiguration provideConfiguration() {
    return config;
  }

  @Provides
  @Singleton
  public DataSourceFactory provideDataSourceFactory() {
    return config.getDataSourceFactory();
  }
}
