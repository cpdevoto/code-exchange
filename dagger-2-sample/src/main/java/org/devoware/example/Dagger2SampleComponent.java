package org.devoware.example;

import javax.inject.Singleton;
import javax.sql.DataSource;

import org.devoware.config.db.DatabaseConfigurationModule;
import org.devoware.example.config.ConfigurationModule;
import org.devoware.example.config.Dagger2SampleConfiguration;
import org.devoware.example.dao.DaoModule;
import org.devoware.example.dao.UserDao;

import dagger.Component;

@Singleton
@Component(modules = {ConfigurationModule.class, DatabaseConfigurationModule.class, DaoModule.class })
public interface Dagger2SampleComponent {
  
  Dagger2SampleConfiguration getConfiguration();
  
  DataSource getDataSource();
  
  UserDao getUserDao();
}
