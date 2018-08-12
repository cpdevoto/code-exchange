package com.resolute.rule.generator.customer.dao;

import java.util.List;

import javax.sql.DataSource;

import com.resolute.common.util.dao.DaoUtils;
import com.resolute.common.util.dao.JdbcStatementFactory;
import com.resolute.rule.generator.common.model.EntityIndex;

import static java.util.Objects.requireNonNull;

class CustomerJdbcDao implements CustomerDao {

  private final JdbcStatementFactory statementFactory;

  static CustomerJdbcDao create(DataSource dataSource) {
    requireNonNull(dataSource, "dataSource cannot be null");
    return new CustomerJdbcDao(dataSource);

  }

  private CustomerJdbcDao(DataSource dataSource) {
    this.statementFactory = JdbcStatementFactory.getInstance(dataSource);
  }

  @Override
  public List<EntityIndex> get() {
    return statementFactory.newStatement()
        .withSql("SELECT id, name FROM customers ORDER BY name")
        .withErrorMessage(
            "A problem occurred while attempting to retrieve customers")
        .executeQuery(
            resultSet -> DaoUtils.processList(resultSet, (idx, rs) -> {
              return EntityIndex.builder()
                  .withId(rs.getInt("id"))
                  .withText(rs.getString("name"))
                  .build();
            }));
  }
}
