package com.doradosystems.mis.scheduler.config;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.devoware.config.db.DataSourceFactory;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MisSchedulerConfiguration {
  @Valid
  @NotNull
  @JsonProperty
  private DataSourceFactory database = new DataSourceFactory();

  public DataSourceFactory getDataSourceFactory() {
      return database;
  }
}
