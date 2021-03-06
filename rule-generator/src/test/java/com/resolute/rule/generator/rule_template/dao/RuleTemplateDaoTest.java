package com.resolute.rule.generator.rule_template.dao;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

import com.resolute.rule.generator.common.model.EntityIndex;
import com.resolute.rule.generator.dao.AbstractDaoTest;
import com.resolute.rule.generator.rule_template.dao.RuleTemplateDao;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import static org.hamcrest.CoreMatchers.equalTo;

public class RuleTemplateDaoTest extends AbstractDaoTest {

  private RuleTemplateDao dao;

  @Before
  public void before() {
    this.dao = RuleTemplateDao.create(dataSource);
  }

  @Test
  public void test_get() {
    List<EntityIndex> templates = dao.get();

    assertNotNull(templates);
    Map<Integer, EntityIndex> templateMap =
        templates.stream().collect(Collectors.toMap(EntityIndex::getId, Function.identity()));

    assertThat(templateMap.containsKey(51335), equalTo(true));
    assertThat(templateMap.get(51335).getText(), equalTo("Invariant"));
  }
}
