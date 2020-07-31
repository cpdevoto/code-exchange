package com.doradosystems.mis.worker.ddeservice;

import javax.annotation.Nonnull;
import javax.inject.Singleton;

import com.doradosystems.mis.worker.ddeservice.shell.ShellBasedDdeService;

import dagger.Module;
import dagger.Provides;

@Module
public class DdeServiceModule {

  @Provides
  @Singleton
  @Nonnull
  public DdeService providesDdeService(ShellBasedDdeService service) {
    return service;
  }
  
}
