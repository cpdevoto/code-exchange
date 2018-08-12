package com.resolute.rule.generator.integration;

import org.junit.ClassRule;
import org.junit.extensions.cpsuite.ClasspathSuite;
import org.junit.rules.RuleChain;
import org.junit.runner.RunWith;

import com.resolute.rule.generator.RuleGeneratorApplication;
import com.resolute.rule.generator.RuleGeneratorConfiguration;
import com.resolute.services.common.test.NetworkUtils;
import com.resolutebi.testutils.dockerdb.DatabaseSeeder;
import com.resolutebi.testutils.dockerdb.DockerDatabase;

import io.dropwizard.testing.ConfigOverride;
import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit.DropwizardAppRule;

@RunWith(ClasspathSuite.class)
@ClasspathSuite.ClassnameFilters({"com.resolute.rule.generator.integration.*Test",
    ".*DaoTest"})
public class IntegrationTestSuite {

  public static final DropwizardAppRule<RuleGeneratorConfiguration> APP =
      getDropWizardAppRule();

  public static final DockerDatabase DB = new DockerDatabase();

  public static final DatabaseSeeder SEEDER = DatabaseSeeder.builder()
      .withSeedScript(IntegrationTestSuite.class, "base-data.sql")
      .withTearDownScript(IntegrationTestSuite.class, "base-teardown.sql")
      .build();

  public static final RuleChain DB_CHAIN = RuleChain
      .outerRule(DB)
      .around(SEEDER);


  @ClassRule
  public static final RuleChain CHAIN = RuleChain
      .outerRule(DB_CHAIN)
      .around(APP);

  private static DropwizardAppRule<RuleGeneratorConfiguration> getDropWizardAppRule() {
    int dropwizardAppPort = NetworkUtils.findFreePort();
    int dropwizardAdminPort = NetworkUtils.findFreePort();

    String databasePort = System.getProperty("docker.postgres.port", "5437");
    String dbUrl = "jdbc:postgresql://localhost:" + databasePort + "/resolute_cloud_dev";

    DropwizardAppRule<RuleGeneratorConfiguration> app =
        new DropwizardAppRule<>(RuleGeneratorApplication.class,
            ResourceHelpers.resourceFilePath("test-config.yml"),
            ConfigOverride.config("server.applicationConnectors[0].port", "" + dropwizardAppPort),
            ConfigOverride.config("server.adminConnectors[0].port", "" + dropwizardAdminPort),
            ConfigOverride.config("postgresqlDatabase.url", dbUrl),
            ConfigOverride.config("postgresqlDatabase.url", dbUrl));

    return app;
  }

}
