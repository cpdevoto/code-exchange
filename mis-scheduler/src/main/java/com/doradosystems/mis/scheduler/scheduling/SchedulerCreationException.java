package com.doradosystems.mis.scheduler.scheduling;

public class SchedulerCreationException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public SchedulerCreationException(Throwable cause) {
    super(cause);
  }

  public SchedulerCreationException(String message) {
    super(message);
  }

  public SchedulerCreationException(String message, Throwable cause) {
    super(message, cause);
  }
}
