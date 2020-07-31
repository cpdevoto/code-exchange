package com.doradosystems.mis.worker.ddeservice;

public class DdeServiceTimeoutException extends DdeServiceException {

  private static final long serialVersionUID = 1L;

  public DdeServiceTimeoutException() {
    super();
  }

  public DdeServiceTimeoutException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  public DdeServiceTimeoutException(String message, Throwable cause) {
    super(message, cause);
  }

  public DdeServiceTimeoutException(String message) {
    super(message);
  }

  public DdeServiceTimeoutException(Throwable cause) {
    super(cause);
  }
}
