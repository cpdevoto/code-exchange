package com.resolute.rule.generator.integration.httpclient;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.resolute.rule.generator.common.model.EntityIndex;
import com.resolute.services.common.test.SimpleHttpRequestFactory;

import static java.util.Objects.requireNonNull;

public class RuleTemplateApiClient {

  private final SimpleHttpRequestFactory requestFactory;

  RuleTemplateApiClient(SimpleHttpRequestFactory requestFactory) {
    this.requestFactory = requireNonNull(requestFactory, "requestFactory cannot be null");
  }

  public List<EntityIndex> get() throws IOException {
    return requestFactory.newRequest()
        .withUrl("/api/rule-templates")
        .execute(new TypeReference<List<EntityIndex>>() {});
  }

}
