package com.resolute.rule.generator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.resolute.rule.generator.customer.CustomerResource;
import com.resolute.rule.generator.customer.dao.CustomerDao;
import com.resolute.rule.generator.rule_instance.RuleInstanceResource;
import com.resolute.rule.generator.rule_instance.dao.RuleInstanceDao;
import com.resolute.rule.generator.rule_template.RuleTemplateResource;
import com.resolute.rule.generator.rule_template.dao.RuleTemplateDao;

import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.db.ManagedDataSource;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class RuleGeneratorApplication extends Application<RuleGeneratorConfiguration> {
  private static final Logger logger = LoggerFactory.getLogger(RuleGeneratorApplication.class);

  public static void main(String[] args) throws Exception {
    new RuleGeneratorApplication().run(args);
  }


  @Override
  public String getName() {
    return "rule-generator";
  }

  @Override
  public void initialize(Bootstrap<RuleGeneratorConfiguration> bootstrap) {
    // Enable variable substitution with environment variables
    bootstrap.setConfigurationSourceProvider(new SubstitutingSourceProvider(
        bootstrap.getConfigurationSourceProvider(), new EnvironmentVariableSubstitutor(false)));
    bootstrap.addBundle(new AssetsBundle("/assets", "/", "index.html"));
  }

  @Override
  public void run(RuleGeneratorConfiguration configuration, Environment environment)
      throws Exception {
    logger.debug("Application running");
    logger.info("Connecting to database at " + configuration.getDataSourceFactory().getUrl());

    environment.jersey().setUrlPattern("/api/*");

    final ManagedDataSource managedDataSource = initDataSource(configuration, environment);

    initCustomerResource(environment, managedDataSource);
    initRuleTemplateResource(environment, managedDataSource);
    initRuleInstanceResource(environment, managedDataSource);

  }

  private ManagedDataSource initDataSource(RuleGeneratorConfiguration configuration,
      Environment environment) {
    final DataSourceFactory dataSourceFactory = configuration.getDataSourceFactory();
    final ManagedDataSource managedDataSource =
        dataSourceFactory.build(environment.metrics(), "JDBC DataSource");
    return managedDataSource;
  }

  private void initCustomerResource(Environment environment, ManagedDataSource managedDataSource) {
    final CustomerDao dao = CustomerDao.create(managedDataSource);
    final CustomerResource resource = new CustomerResource(dao);
    environment.jersey().register(resource);
  }

  private void initRuleTemplateResource(Environment environment,
      ManagedDataSource managedDataSource) {
    final RuleTemplateDao dao = RuleTemplateDao.create(managedDataSource);
    final RuleTemplateResource resource = new RuleTemplateResource(dao);
    environment.jersey().register(resource);
  }

  private void initRuleInstanceResource(Environment environment,
      ManagedDataSource managedDataSource) {
    final RuleInstanceDao dao = RuleInstanceDao.create(managedDataSource);
    final RuleInstanceResource resource = new RuleInstanceResource(dao);
    environment.jersey().register(resource);
  }
}
