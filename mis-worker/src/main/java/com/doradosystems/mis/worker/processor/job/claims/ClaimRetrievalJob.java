package com.doradosystems.mis.worker.processor.job.claims;

import static java.util.Objects.requireNonNull;

import javax.annotation.Nonnull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codahale.metrics.Counter;
import com.doradosystems.mis.agent.model.ClaimRetrievalTask;

public class ClaimRetrievalJob implements Runnable {
  private static Logger log = LoggerFactory.getLogger(ClaimRetrievalJob.class);
  
  private final ClaimRetrievalTask task;
  private final Counter jobQueueCounter;
  
  public ClaimRetrievalJob(@Nonnull ClaimRetrievalTask task, @Nonnull Counter jobQueueCounter) {
    this.task = requireNonNull(task);
    this.jobQueueCounter = requireNonNull(jobQueueCounter);
  }

  @Override
  public void run() {
    jobQueueCounter.dec();
    log.info("Executing task: " + task);
  }
}
