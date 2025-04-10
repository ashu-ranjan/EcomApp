package com.ecommerce.exception;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(String message) {
        super(message);
    }

  public OrderNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }

  public OrderNotFoundException(Throwable cause) {
    super(cause);
  }

  public OrderNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  public OrderNotFoundException() {
  }
}
