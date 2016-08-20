package com.doradosystems.mis.worker.dao;

import static java.util.Objects.requireNonNull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.sql.DataSource;

import org.devoware.config.db.util.DataAccessException;

import com.doradosystems.mis.agent.model.Claim;

public class JdbcClaimDao implements ClaimDao {
  
  private final DataSource dataSource;

  @Inject
  public JdbcClaimDao(@Nonnull DataSource dataSource) {
    this.dataSource = requireNonNull(dataSource);
  }


  @Override
  public void insertOrUpdate(@Nonnull Claim claim) {
    try (Connection connection = dataSource.getConnection()) {
      if(exists(connection, claim.getClaimNumber())){
        update(connection, claim);
      } else {
        insert(connection, claim);
      } 
    } catch (SQLException e) {
      throw new DataAccessException("A problem occurred while attempting to establish a connection to the database", e);
    }
  }


  private boolean exists(Connection connection, String claimNumber) {
    boolean exists = false;
    String selectSQL = "SELECT 1 FROM CLAIMS WHERE claim_number = ?";
    try (PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) { 
        preparedStatement.setString(1, claimNumber);
        try (ResultSet resultSet = preparedStatement.executeQuery()) {
          if(resultSet.next()){
              exists = true;
          }
        }
    } catch (SQLException e) {
      throw new DataAccessException("A problem occurred while attempting to check whether a claim exists", e);
    }
    return exists;
 }


  private void update(Connection connection, Claim claim) {
    // TODO Auto-generated method stub
    
  }


  private void insert(Connection connection, Claim claim) {
    // TODO Auto-generated method stub
    
  }

}
