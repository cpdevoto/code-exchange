package com.doradosystems.mis.scheduler.dao;

import static java.util.Objects.requireNonNull;
import static org.devoware.homonculus.database.util.DaoUtils.processList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.sql.DataSource;

import org.devoware.homonculus.database.util.DataAccessException;

import com.doradosystems.mis.agent.model.NationalProviderIdentifier;
import com.google.common.collect.Lists;

public class JdbcNpiDao implements NpiDao {

  private final DataSource dataSource;

  @Inject
  public JdbcNpiDao(@Nonnull DataSource dataSource) {
    this.dataSource = requireNonNull(dataSource);
  }

  @Override
  @Nonnull
  public List<NationalProviderIdentifier> getAll() {
    List<NationalProviderIdentifier> npis = Lists.newArrayList();
    String sql = "SELECT id, id_number, region_code FROM npis";
    try (Connection conn = dataSource.getConnection()) {
      try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        try (ResultSet resultSet = stmt.executeQuery()) {
          npis.addAll(processList(resultSet, (rowNum, rs) -> {
            return NationalProviderIdentifier.builder()
                .withId(rs.getLong("id"))
                .withIdNumber(rs.getLong("id_number"))
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
