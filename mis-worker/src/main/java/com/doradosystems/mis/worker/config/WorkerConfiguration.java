package com.doradosystems.mis.worker.config;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.devoware.validators.util.Duration;
import org.devoware.validators.validation.MinDuration;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WorkerConfiguration {
  
  @MinDuration(0)
  @JsonProperty
  private Duration shutdownTime = Duration.seconds(5);
  
  @Min(1)
  @NotNull
  @JsonProperty
  private int numWorkers;

  @MinDuration(0)
  @JsonProperty
  private Duration keepAliveTime = Duration.seconds(60);
  
  @Min(1)
  @JsonProperty
  private int maxJobsInQueue = Integer.MAX_VALUE;

  public Duration getShutdownTime() {
    return shutdownTime;
  }

  public int getNumWorkers() {
    return numWorkers;
  }
  
  public int getMinThreads() {
    return numWorkers;
  }

  public int getMaxThreads() {
    return numWorkers;
  }

  public Duration getKeepAliveTime() {
    return keepAliveTime;
  }

  public int getMaxJobsInQueue() {
    return maxJobsInQueue;
  }
}
