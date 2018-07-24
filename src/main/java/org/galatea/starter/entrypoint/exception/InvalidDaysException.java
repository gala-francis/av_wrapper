package org.galatea.starter.entrypoint.exception;


public class InvalidDaysException extends RuntimeException {

  public InvalidDaysException() {
    super("Invalid number of days. Days must be a positive integer. ");
  }

}
