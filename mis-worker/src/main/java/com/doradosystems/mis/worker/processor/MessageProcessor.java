package com.doradosystems.mis.worker.processor;

import static com.doradosystems.mis.worker.WorkerQualifiers.JOB_QUEUE_COUNTER;
import static java.util.Objects.requireNonNull;

import java.util.concurrent.ExecutorService;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Named;

import org.devoware.homonculus.messaging.input.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codahale.metrics.Counter;
import com.doradosystems.mis.worker.processor.job.Jobs;
import com.google.common.eventbus.Subscribe;

public class MessageProcessor {
  private static final Logger log = LoggerFactory.getLogger(MessageProcessor.class);
  
  private final Jobs jobs;
  private final ExecutorService executorService;
  private final Counter queueCounter;

  @Inject
  public MessageProcessor(@Nonnull Jobs jobs, @Nonnull ExecutorService executorService, 
      @Nonnull @Named(JOB_QUEUE_COUNTER) Counter queueCounter) {
    this.jobs = requireNonNull(jobs);
    this.executorService = requireNonNull(executorService);
    this.queueCounter = requireNonNull(queueCounter);
  }
  
  @Subscribe
  public void process(Message message) {
    try {
      Runnable job = jobs.createJob(message);
      if (job == null) {
        return;
      }
      queueCounter.inc();
      executorService.execute(job);
    } catch (Throwable t) {
      log.error("A problem occurred while attempting to process message " + message, t);
    }
  }

}
