package com.doradosystems.mis.worker.processor.job.claims;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javax.annotation.Nonnull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codahale.metrics.Counter;
import com.codahale.metrics.Meter;
import com.doradosystems.mis.agent.model.Claim;
import com.doradosystems.mis.agent.model.ClaimRetrievalTask;
import com.doradosystems.mis.worker.dao.ClaimDao;
import com.doradosystems.mis.worker.ddeservice.DdeService;

public class ClaimRetrievalJob implements Runnable {
  private static Logger log = LoggerFactory.getLogger(ClaimRetrievalJob.class);

  private final ClaimRetrievalTask task;
  private final DdeService ddeService;
  private final ClaimDao claimDao;
  private final Counter jobQueueCounter;
  private final Meter claimsRetrieved;

  public ClaimRetrievalJob(@Nonnull ClaimRetrievalTask task, @Nonnull DdeService ddeService,
      @Nonnull ClaimDao claimDao, @Nonnull Counter jobQueueCounter, @Nonnull Meter claimsRetrieved) {
    this.task = requireNonNull(task);
    this.ddeService = requireNonNull(ddeService);
    this.claimDao = requireNonNull(claimDao);
    this.jobQueueCounter = requireNonNull(jobQueueCounter);
    this.claimsRetrieved = requireNonNull(claimsRetrieved);
  }

  @Override
  public void run() {
    jobQueueCounter.dec();
    try {
      List<Claim> claims = ddeService.getClaims(task);
      claimsRetrieved.mark(claims.size());
      for (Claim claim : claims) {
        claimDao.insertOrUpdate(claim);
      }
      log.info("Successful execution of claim retrieval task: " + task);
    } catch (Throwable t) {
      log.error("A problem occurred while attempting to execute claim retrieval task " + task, t);
    }
  }
}
