package com.doradosystems.mis.worker.dao;

import static java.util.Objects.requireNonNull;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.sql.DataSource;

import com.doradosystems.mis.agent.model.Claim;

public class JdbcClaimDao implements ClaimDao {
  
  private final DataSource dataSource;

  @Inject
  public JdbcClaimDao(@Nonnull DataSource dataSource) {
    this.dataSource = requireNonNull(dataSource);
  }


  @Override
  public void upsertClaim(Claim claim) {
    System.out.println(dataSource);
    // TODO Auto-generated method stub
  }

}
