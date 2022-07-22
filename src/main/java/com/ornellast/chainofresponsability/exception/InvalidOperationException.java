package com.ornellast.chainofresponsability.exception;

public class InvalidOperationException extends RuntimeException {

  private static final String DEFAULT_MESSAGE = "An invalid operation was about to be done! %s";

  public InvalidOperationException() {
    super(String.format(DEFAULT_MESSAGE, ""));
  }

  public InvalidOperationException(String message) {
    super(String.format(DEFAULT_MESSAGE, message));
  }
}
