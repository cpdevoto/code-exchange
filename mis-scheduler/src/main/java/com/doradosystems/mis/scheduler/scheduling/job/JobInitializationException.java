package com.doradosystems.mis.scheduler.scheduling.job;

public class JobInitializationException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public JobInitializationException() {
  }

  public JobInitializationException(String message) {
    super(message);
  }

  public JobInitializationException(Throwable cause) {
    super(cause);
  }

  public JobInitializationException(String message, Throwable cause) {
    super(message, cause);
  }

  public JobInitializationException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

}
