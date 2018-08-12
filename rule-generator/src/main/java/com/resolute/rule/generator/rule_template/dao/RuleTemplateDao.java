package com.resolute.rule.generator.rule_template.dao;

import java.util.List;

import javax.sql.DataSource;

import com.resolute.rule.generator.common.model.EntityIndex;

public interface RuleTemplateDao {

  public static RuleTemplateDao create(DataSource dataSource) {
    return RuleTemplateJdbcDao.create(dataSource);
  }

  public List<EntityIndex> get();

}
