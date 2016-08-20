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

import com.doradosystems.mis.agent.model.OperatorNpi;
import com.google.common.collect.Lists;

public class JdbcOperatorNpiDao implements OperatorNpiDao {

  private final DataSource dataSource;

  @Inject
  public JdbcOperatorNpiDao(@Nonnull DataSource dataSource) {
    this.dataSource = requireNonNull(dataSource);
  }

  @Override
  @Nonnull
  public List<OperatorNpi> getAll() {
    List<OperatorNpi> operatorNpis = Lists.newArrayList();
    String sql = "SELECT operator_id, npi_id FROM operators_npis";
    try (Connection conn = dataSource.getConnection()) {
      try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        try (ResultSet resultSet = stmt.executeQuery()) {
          operatorNpis.addAll(processList(resultSet, (rowNum, rs) -> {
            return OperatorNpi.builder()
                .withOperatorId(rs.getLong("operator_id"))
                .withNpiId(rs.getLong("npi_id"))
                .build();
          }));
        }
      }
    } catch (SQLException e) {
      throw new DataAccessException("A problem occurred while attempting to retrieve operator npis", e);
    }
    return operatorNpis;
  }


}
