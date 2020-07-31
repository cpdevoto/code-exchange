package com.doradosystems.mis.worker.ddeservice.shell;

import java.util.List;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import com.doradosystems.mis.agent.model.Claim;
import com.doradosystems.mis.agent.model.ClaimRetrievalTask;
import com.doradosystems.mis.worker.ddeservice.DdeService;
import com.doradosystems.mis.worker.ddeservice.DdeServiceException;

public class ShellBasedDdeService implements DdeService {

  private final ShellBasedRetrieveClaimsService retrieveClaimsService;

  @Inject
  public ShellBasedDdeService(@Nonnull ShellBasedRetrieveClaimsService retrieveClaimsService) {
    this.retrieveClaimsService = retrieveClaimsService;
  }

  @Override
  @Nonnull
  public List<Claim> getClaims(@Nonnull ClaimRetrievalTask claimRetrievalTask)
      throws DdeServiceException {
    return retrieveClaimsService.getClaims(claimRetrievalTask);
  }
}
