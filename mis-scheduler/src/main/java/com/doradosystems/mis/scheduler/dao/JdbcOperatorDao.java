package com.doradosystems.mis.scheduler.dao;

import static java.util.Objects.requireNonNull;
import static org.devoware.config.db.util.DaoUtils.processList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.sql.DataSource;

import org.devoware.config.db.util.DataAccessException;

import com.doradosystems.mis.agent.model.Operator;
import com.google.common.collect.Lists;

public class JdbcOperatorDao implements OperatorDao {

  private final DataSource dataSource;

  @Inject
  public JdbcOperatorDao(@Nonnull DataSource dataSource) {
    this.dataSource = requireNonNull(dataSource);
  }

  @Override
  @Nonnull
  public List<Operator> getOperators() {
    List<Operator> operators = Lists.newArrayList();
    String sql = "SELECT * FROM operator ORDER BY id";
    try (Connection conn = dataSource.getConnection()) {
      try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        try (ResultSet resultSet = stmt.executeQuery()) {
          operators.addAll(processList(resultSet, (rowNum, rs) -> {
            return Operator.builder()
                .withId(rs.getLong("id"))
                .withUserName(rs.getString("user_name"))
                .withPassword(rs.getString("password"))
                .build();
          }));
        }
      }
    } catch (SQLException e) {
      throw new DataAccessException("A problem occurred while attempting to retrieve operators", e);
    }
    return operators;
  }


}
