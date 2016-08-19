package com.doradosystems.mis.worker.config;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.devoware.config.db.DataSourceFactory;

import com.doradosystem.messaging.config.RabbitMqConfiguration;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ApplicationConfiguration {
  @Valid
  @NotNull
  @JsonProperty
  private DataSourceFactory database;

  @Valid
  @NotNull
  @JsonProperty
  private RabbitMqConfiguration rabbitMq;

  @Valid
  @NotNull
  @JsonProperty
  private WorkerConfiguration workers;

  public DataSourceFactory getDataSourceFactory() {
      return database;
  }
  
  public RabbitMqConfiguration getRabbitMq() {
    return rabbitMq;
  }
  
  public WorkerConfiguration getWorker() {
    return workers;
  }
  
  
}
