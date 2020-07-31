package com.doradosystems.mis.worker.ddeservice;

import java.util.List;

import com.doradosystems.mis.agent.model.Claim;
import com.doradosystems.mis.agent.model.ClaimRetrievalTask;

public interface DdeService {
  
  public List<Claim> getClaims(ClaimRetrievalTask claimRetrievalTask) throws DdeServiceException;

}
