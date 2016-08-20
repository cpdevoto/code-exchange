package com.doradosystems.mis.worker.dao;

import com.doradosystems.mis.agent.model.Claim;

public interface ClaimDao {
  
  public void insertOrUpdate(Claim claim);

}
