package com.doradosystems.mis.scheduler.dao;

import javax.annotation.Nonnull;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DaoModule {

  @Provides
  @Singleton
  @Nonnull
  public OperatorDao provideOperatorDao(JdbcOperatorDao dao) {
    return dao;
  }

  @Provides
  @Singleton
  @Nonnull
  public OperatorNpiDao provideOperatorNpiDao(JdbcOperatorNpiDao dao) {
    return dao;
  }

  @Provides
  @Singleton
  @Nonnull
  public NpiDao provideNpiDao(JdbcNpiDao dao) {
    return dao;
  }
}
