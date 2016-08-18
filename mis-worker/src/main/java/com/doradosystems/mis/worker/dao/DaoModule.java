package com.doradosystems.mis.worker.dao;

import javax.annotation.Nonnull;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DaoModule {

  @Provides
  @Singleton
  @Nonnull
  public ClaimDao provideUserDao(JdbcClaimDao dao) {
    return dao;
  }

}
