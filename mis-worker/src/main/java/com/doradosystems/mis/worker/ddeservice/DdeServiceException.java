package com.doradosystems.mis.worker.ddeservice;

public class DdeServiceException extends Exception {

  private static final long serialVersionUID = 1L;

  public DdeServiceException() {
  }

  public DdeServiceException(String message) {
    super(message);
  }

  public DdeServiceException(Throwable cause) {
    super(cause);
  }

  public DdeServiceException(String message, Throwable cause) {
    super(message, cause);
  }

  public DdeServiceException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

}
