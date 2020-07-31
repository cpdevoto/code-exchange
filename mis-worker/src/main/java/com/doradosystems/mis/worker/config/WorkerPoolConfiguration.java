package com.doradosystems.mis.worker.config;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.devoware.homonculus.validators.util.Duration;
import org.devoware.homonculus.validators.validation.ComparisonOperator;
import org.devoware.homonculus.validators.validation.FieldCompare;
import org.devoware.homonculus.validators.validation.MinDuration;

import com.fasterxml.jackson.annotation.JsonProperty;

@FieldCompare(first = "minWorkers", operator = ComparisonOperator.LESS_THAN_OR_EQUALS,
    second = "maxWorkers", fieldClass = Integer.class)
public class WorkerPoolConfiguration {

  @MinDuration(0)
  @JsonProperty
  private Duration shutdownTime = Duration.seconds(5);

  @Min(0)
  @NotNull
  @JsonProperty
  private int minWorkers;

  @Min(1)
  @NotNull
  @JsonProperty
  private int maxWorkers;

  @MinDuration(0)
  @JsonProperty
  private Duration keepAliveTime = Duration.seconds(60);

  @Min(1)
  @JsonProperty
  private int maxJobsInQueue = Integer.MAX_VALUE;

  public Duration getShutdownTime() {
    return shutdownTime;
  }

  public int getMinWorkers() {
    return minWorkers;
  }

  public int getMaxWorkers() {
    return maxWorkers;
  }

  public Duration getKeepAliveTime() {
    return keepAliveTime;
  }

  public int getMaxJobsInQueue() {
    return maxJobsInQueue;
  }
}
