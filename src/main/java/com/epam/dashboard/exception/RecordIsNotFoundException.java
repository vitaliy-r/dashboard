package com.epam.dashboard.exception;

public class RecordIsNotFoundException extends ValidationException {

  private static final String DEFAULT_MESSAGE = "Object is not found in database";

  public RecordIsNotFoundException() {
    super(DEFAULT_MESSAGE);
  }

  public RecordIsNotFoundException(String errorMsg) {
    super(errorMsg);
  }

  public RecordIsNotFoundException(Exception e) {
    super(DEFAULT_MESSAGE, e);
  }

  public RecordIsNotFoundException(String errorMsg, Exception e) {
    super(errorMsg, e);
  }

}
