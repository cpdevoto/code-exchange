package com.doradosystems.mis.scheduler;

import javax.inject.Singleton;
import javax.sql.DataSource;

import org.devoware.config.db.DatabaseConfigurationModule;
import org.devoware.homonculus.setup.Environment;

import com.doradosystems.mis.scheduler.config.ConfigurationModule;
import com.doradosystems.mis.scheduler.config.MisSchedulerConfiguration;
import com.doradosystems.mis.scheduler.setup.EnvironmentModule;

import dagger.Component;

@Singleton
@Component(modules = {ConfigurationModule.class, DatabaseConfigurationModule.class, EnvironmentModule.class })
public interface MisSchedulerComponent {
  
  MisSchedulerConfiguration getConfiguration();
  
  DataSource getDataSource();
  
  Environment getEnvironment();
}
