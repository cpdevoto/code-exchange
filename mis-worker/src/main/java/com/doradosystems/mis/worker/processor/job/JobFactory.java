package com.doradosystems.mis.worker.processor.job;

import com.doradosystems.messaging.input.Message;

public interface JobFactory {

  public Runnable createJob (Message message) throws JobCreationException;
}
