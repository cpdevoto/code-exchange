package com.resolute.rule.generator.rule_instance.dao;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableList;
import com.resolute.common.util.dao.DataAccessException;
import com.resolute.rule.generator.common.model.RuleInstances;
import com.resolute.rule.generator.dao.AbstractDaoTest;

import static org.junit.Assert.assertNotNull;

public class RuleInstanceDaoTest extends AbstractDaoTest {

  private RuleInstanceDao dao;

  @Before
  public void before() {
    this.dao = RuleInstanceDao.create(dataSource);
  }

  @Test
  public void test_get() {
    RuleInstances instances = dao.get(4, 4);

    assertNotNull(instances);
  }

  @Test(expected = DataAccessException.class)
  public void test_generate() {
    dao.generate(4, 4, ImmutableList.of(22031));

  }
}
