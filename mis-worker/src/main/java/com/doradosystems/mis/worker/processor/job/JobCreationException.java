package com.doradosystems.mis.worker.processor.job;

public class JobCreationException extends Exception {

  private static final long serialVersionUID = 1L;

  public JobCreationException() {
  }

  public JobCreationException(String message) {
    super(message);
  }

  public JobCreationException(Throwable cause) {
    super(cause);
  }

  public JobCreationException(String message, Throwable cause) {
    super(message, cause);
  }

  public JobCreationException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

}
