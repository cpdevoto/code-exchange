package com.doradosystems.mis.scheduler.dao;

import java.util.List;

import com.doradosystems.mis.agent.model.NationalProviderIdentifier;

public interface NpiDao {
  
  public List<NationalProviderIdentifier> getAll();

}
