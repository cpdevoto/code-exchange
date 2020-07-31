package com.doradosystems.mis.worker.processor.job.claims;

import static com.codahale.metrics.MetricRegistry.name;
import static com.doradosystems.mis.worker.WorkerQualifiers.JOB_QUEUE_COUNTER;
import static java.util.Objects.requireNonNull;

import java.io.IOException;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Named;

import org.devoware.homonculus.messaging.input.Message;

import com.codahale.metrics.Counter;
import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;
import com.doradosystems.mis.agent.model.ClaimRetrievalTask;
import com.doradosystems.mis.worker.dao.ClaimDao;
import com.doradosystems.mis.worker.ddeservice.DdeService;
import com.doradosystems.mis.worker.processor.job.JobCreationException;
import com.doradosystems.mis.worker.processor.job.JobFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ClaimRetrievalJobFactory implements JobFactory {
  
  private final ObjectMapper mapper;
  private final DdeService ddeService;
  private final ClaimDao claimDao;
  private final Counter jobQueueCounter;
  private final Meter claimsRetrieved;
  
  @Inject
  public ClaimRetrievalJobFactory(@Nonnull ObjectMapper mapper,
      @Nonnull DdeService ddeService, @Nonnull ClaimDao claimDao,
      @Named(JOB_QUEUE_COUNTER) @Nonnull Counter jobQueueCounter,
      @Nonnull MetricRegistry metrics) {
    this.mapper = requireNonNull(mapper);
    this.ddeService = requireNonNull(ddeService);
    this.claimDao = requireNonNull(claimDao);
    this.jobQueueCounter = requireNonNull(jobQueueCounter);
    this.claimsRetrieved = metrics.meter(name(ClaimRetrievalJob.class, "claims-retrieved"));
  }

  @Override
  public Runnable createJob(Message message) throws JobCreationException {
    try {
      ClaimRetrievalTask task = mapper.readValue(message.getBodyAsString(), ClaimRetrievalTask.class);
      return new ClaimRetrievalJob(task, ddeService, claimDao, jobQueueCounter, claimsRetrieved);
    } catch (IOException e) {
      throw new JobCreationException("A problem occurred while attempting to create a job for a claim retrieval task", e);
    }
  }

}
