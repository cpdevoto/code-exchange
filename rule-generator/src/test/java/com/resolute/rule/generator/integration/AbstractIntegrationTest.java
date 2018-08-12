package com.resolute.rule.generator.integration;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.rules.RuleChain;

import com.resolute.rule.generator.RuleGeneratorConfiguration;
import com.resolute.rule.generator.integration.httpclient.HttpClient;

import io.dropwizard.testing.junit.DropwizardAppRule;

public abstract class AbstractIntegrationTest {
  @ClassRule
  public static final DropwizardAppRule<RuleGeneratorConfiguration> APP =
      IntegrationTestSuite.APP;

  @ClassRule
  public static final RuleChain CHAIN = IntegrationTestSuite.CHAIN;

  protected static HttpClient client;

  @Before
  public void setupHttpClient() {
    // Since 'client' is a static member, we would ideally initialize it in an @BeforeClass method.
    // Unfortunately, doing introduces a race condition, since APP may not be initialized yet
    // depending on the order in which classes are loaded. We use synchronization blocks to ensure
    // that it is only initialized once.
    HttpClient tempClient = client;
    if (tempClient == null) {
      synchronized (getClass()) {
        tempClient = client;
        if (tempClient == null) {
          client = tempClient = HttpClient.getClient(APP.getLocalPort());
        }
      }
    }
  }

}
