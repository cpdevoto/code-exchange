package com.doradosystems.mis.worker.processor;

public class MessageProcessingException extends RuntimeException {
  
  private static final long serialVersionUID = 1L;

  public MessageProcessingException() {}

  public MessageProcessingException(String message) {
    super(message);
  }

  public MessageProcessingException(Throwable cause) {
    super(cause);
  }

  public MessageProcessingException(String message, Throwable cause) {
    super(message, cause);
  }

  public MessageProcessingException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

}
