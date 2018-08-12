package com.resolute.rule.generator.rule_template.dao;

import java.util.List;

import javax.sql.DataSource;

import com.resolute.common.util.dao.DaoUtils;
import com.resolute.common.util.dao.JdbcStatementFactory;
import com.resolute.rule.generator.common.model.EntityIndex;

import static java.util.Objects.requireNonNull;

class RuleTemplateJdbcDao implements RuleTemplateDao {

  private final JdbcStatementFactory statementFactory;

  static RuleTemplateJdbcDao create(DataSource dataSource) {
    requireNonNull(dataSource, "dataSource cannot be null");
    return new RuleTemplateJdbcDao(dataSource);

  }

  private RuleTemplateJdbcDao(DataSource dataSource) {
    this.statementFactory = JdbcStatementFactory.getInstance(dataSource);
  }

  @Override
  public List<EntityIndex> get() {
    return statementFactory.newStatement()
        .withSql(
            "SELECT id, fault_number, display_name FROM ad_rule_templates WHERE active = true ORDER BY name")
        .withErrorMessage(
            "A problem occurred while attempting to retrieve customers")
        .executeQuery(
            resultSet -> DaoUtils.processList(resultSet, (idx, rs) -> {
              String faultNumber = rs.getString("fault_number");
              String text = rs.getString("display_name")
                  + (faultNumber != null ? " (" + faultNumber + ")" : "");
              return EntityIndex.builder()
                  .withId(rs.getInt("id"))
                  .withText(text)
                  .build();
            }));
  }
}
