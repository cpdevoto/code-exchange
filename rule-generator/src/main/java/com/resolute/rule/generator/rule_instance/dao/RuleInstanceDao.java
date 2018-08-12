package com.resolute.rule.generator.rule_instance.dao;

import java.util.List;

import javax.sql.DataSource;

import com.resolute.rule.generator.common.model.RuleInstances;

public interface RuleInstanceDao {

  public static RuleInstanceDao create(DataSource dataSource) {
    return RuleInstanceJdbcDao.create(dataSource);
  }

  public RuleInstances get(int customerId, int ruleTemplateId);

  public void generate(int customerId, int ruleTemplateId, List<Integer> equipmentIds);
}
