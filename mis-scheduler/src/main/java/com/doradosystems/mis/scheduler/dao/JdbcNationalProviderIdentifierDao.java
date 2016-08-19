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

import com.doradosystems.mis.agent.model.NationalProviderIdentifier;
import com.google.common.collect.Lists;

public class JdbcNationalProviderIdentifierDao implements NationalProviderIdentifierDao {

  private final DataSource dataSource;

  @Inject
  public JdbcNationalProviderIdentifierDao(@Nonnull DataSource dataSource) {
    this.dataSource = requireNonNull(dataSource);
  }

  @Override
  @Nonnull
  public List<NationalProviderIdentifier> getNationalProviderIdentifiers() {
    List<NationalProviderIdentifier> npis = Lists.newArrayList();
    String sql = "SELECT * FROM npi ORDER BY id";
    try (Connection conn = dataSource.getConnection()) {
      try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        try (ResultSet resultSet = stmt.executeQuery()) {
          npis.addAll(processList(resultSet, (rowNum, rs) -> {
            return NationalProviderIdentifier.builder()
                .withId(rs.getLong("id"))
                .withOperatorId(rs.getLong("operator_id"))
                .withRegionCode(rs.getString("region_code"))
                .build();
          }));
        }
      }
    } catch (SQLException e) {
      throw new DataAccessException("A problem occurred while attempting to retrieve NPIs", e);
    }
    return npis;
  }


}