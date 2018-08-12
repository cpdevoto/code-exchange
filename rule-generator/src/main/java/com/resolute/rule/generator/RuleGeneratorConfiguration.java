package com.resolute.rule.generator;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;

public class RuleGeneratorConfiguration extends Configuration {

  @Valid
  @NotNull
  @JsonProperty("postgresqlDatabase")
  private DataSourceFactory dataSourceFactory;

  public DataSourceFactory getDataSourceFactory() {
    return dataSourceFactory;
  }

}
