package com.doradosystems.mis.worker.processor.job;

import org.devoware.homonculus.messaging.input.Message;

public interface JobFactory {

  public Runnable createJob (Message message) throws JobCreationException;
}
