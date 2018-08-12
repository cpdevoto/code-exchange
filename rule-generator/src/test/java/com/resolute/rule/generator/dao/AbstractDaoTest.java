package com.resolute.rule.generator.dao;

import java.io.IOException;

import javax.sql.DataSource;

import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.rules.RuleChain;

import com.resolute.rule.generator.integration.IntegrationTestSuite;

public class AbstractDaoTest {

  @ClassRule
  public static final RuleChain DB_CHAIN = IntegrationTestSuite.DB_CHAIN;

  protected static DataSource dataSource;

  @BeforeClass
  public static void setup() throws IOException {
    dataSource = IntegrationTestSuite.SEEDER.getDataSource();
  }


}
