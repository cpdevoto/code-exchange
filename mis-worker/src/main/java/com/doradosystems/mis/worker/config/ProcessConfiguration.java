package com.doradosystems.mis.worker.config;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProcessConfiguration {

  @NotNull
  @JsonProperty
  private String name;
  
  @NotNull
  @JsonProperty
  private String path;

  public String getName() {
    return name;
  }

  public String getPath() {
    return path;
  }
}
