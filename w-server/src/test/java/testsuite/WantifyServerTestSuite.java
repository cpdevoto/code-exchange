package testsuite;

import org.junit.ClassRule;
import org.junit.extensions.cpsuite.ClasspathSuite;
import org.junit.rules.RuleChain;
import org.junit.runner.RunWith;

import testutils.rules.dockerdb.DatabaseSeeder;
import testutils.rules.dockerdb.DockerDatabase;

@RunWith(ClasspathSuite.class)
@ClasspathSuite.ClassnameFilters({".*Test"})
public class WantifyServerTestSuite {

  private static final DockerDatabase dockerDatabase = new DockerDatabase();

  private static final DatabaseSeeder seeder = DatabaseSeeder.builder()
      .withSeedScript(WantifyServerTestSuite.class, "base-data.sql")
      .withTearDownScript(WantifyServerTestSuite.class, "base-teardown.sql")
      .build();

  @ClassRule
  public static final RuleChain CHAIN = RuleChain
      .outerRule(dockerDatabase)
      .around(seeder);


  public WantifyServerTestSuite() {}

}
