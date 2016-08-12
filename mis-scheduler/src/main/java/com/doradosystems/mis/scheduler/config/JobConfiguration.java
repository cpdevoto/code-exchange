package com.doradosystems.mis.scheduler.config;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JobConfiguration {

  @NotNull
  @JsonProperty
  private String name;
  
  @NotNull
  @JsonProperty
  private String recurrenceRule;

  public String getName() {
    return name;
  }

  public String getRecurrenceRule() {
    return recurrenceRule;
  }
  
}
