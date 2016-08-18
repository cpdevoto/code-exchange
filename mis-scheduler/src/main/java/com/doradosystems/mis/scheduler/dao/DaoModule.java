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
  public OperatorDao provideUserDao(JdbcOperatorDao dao) {
    return dao;
  }

  @Provides
  @Singleton
  @Nonnull
  public NationalProviderIdentifierDao provideNpiDao(JdbcNationalProviderIdentifierDao dao) {
    return dao;
  }
}
