package com.doradosystems.mis.scheduler.config;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.devoware.config.db.DataSourceFactory;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;

public class MisSchedulerConfiguration {
  @Valid
  @NotNull
  @JsonProperty
  private DataSourceFactory database;

  @Valid
  @NotEmpty
  @JsonProperty
  private List<JobConfiguration> jobs;

  public DataSourceFactory getDataSourceFactory() {
      return database;
  }
  
  public List<JobConfiguration> getJobs() {
    return ImmutableList.copyOf(jobs);
  }
}
