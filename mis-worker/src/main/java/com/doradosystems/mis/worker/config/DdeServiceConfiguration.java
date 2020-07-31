package com.doradosystems.mis.worker.config;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.validation.constraints.NotNull;

import org.devoware.homonculus.validators.util.Duration;
import org.devoware.homonculus.validators.validation.ContainsKeys;
import org.devoware.homonculus.validators.validation.MinDuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableMap;

public class DdeServiceConfiguration {
  public static final String RETRIEVE_CLAIMS_PROCESS = "retrieveClaims";

  @MinDuration(value=1,unit=TimeUnit.MINUTES)
  @JsonProperty
  private Duration serviceTimeout = Duration.minutes(1);
  
  @NotNull
  @ContainsKeys(RETRIEVE_CLAIMS_PROCESS)
  @JsonProperty
  private Map<String, String> processPaths;

  public Map<String, String> getProcessPaths() {
    return ImmutableMap.copyOf(processPaths);
  }
  
  public Duration getServiceTimeout() {
    return serviceTimeout;
  }
  
  public String getProcessPath(String processName) {
    return processPaths.get(processName);
  }

  

}
