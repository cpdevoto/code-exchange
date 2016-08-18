package com.doradosystems.mis.worker.processor.job.claims;

import static java.util.Objects.requireNonNull;

import java.io.IOException;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Named;

import com.codahale.metrics.Counter;
import com.doradosystems.messaging.input.Message;
import com.doradosystems.mis.agent.model.ClaimRetrievalTask;
import com.doradosystems.mis.worker.processor.job.JobCreationException;
import com.doradosystems.mis.worker.processor.job.JobFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ClaimRetrievalJobFactory implements JobFactory {
  
  private final ObjectMapper mapper;
  private final Counter jobQueueCounter;
  
  @Inject
  public ClaimRetrievalJobFactory(@Nonnull ObjectMapper mapper, 
      @Named("job-queue-counter") @Nonnull Counter jobQueueCounter) {
    this.mapper = requireNonNull(mapper);
    this.jobQueueCounter = requireNonNull(jobQueueCounter);
  }

  @Override
  public Runnable createJob(Message message) throws JobCreationException {
    try {
      ClaimRetrievalTask task = mapper.readValue(message.getBodyAsString(), ClaimRetrievalTask.class);
      return new ClaimRetrievalJob(task, jobQueueCounter);
    } catch (IOException e) {
      throw new JobCreationException("A problem occurred while attempting to create a job for a claim retrieval task", e);
    }
  }

}
