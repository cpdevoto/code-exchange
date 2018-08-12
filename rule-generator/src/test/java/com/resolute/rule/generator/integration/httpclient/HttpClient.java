package com.resolute.rule.generator.integration.httpclient;

import com.resolute.services.common.test.SimpleHttpRequestFactory;

public class HttpClient {

  private final CustomerApiClient customerApi;
  private final RuleTemplateApiClient ruleTemplateApi;

  public static HttpClient getClient(int serverPort) {
    return new HttpClient(serverPort);
  }

  private HttpClient(int serverPort) {
    SimpleHttpRequestFactory requestFactory =
        SimpleHttpRequestFactory.builder(serverPort)
            .build();
    this.customerApi = new CustomerApiClient(requestFactory);
    this.ruleTemplateApi = new RuleTemplateApiClient(requestFactory);
  }

  public CustomerApiClient customerApi() {
    return customerApi;
  }

  public RuleTemplateApiClient ruleTemplateApi() {
    return ruleTemplateApi;
  }
}

