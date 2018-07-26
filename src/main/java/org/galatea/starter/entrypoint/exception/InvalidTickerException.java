package org.galatea.starter.entrypoint.exception;

public class InvalidTickerException extends RuntimeException {

  public InvalidTickerException() {
    super("Invalid ticker. Tickers must be between one and five"
        + " characters, and only contain letters.");
  }
}
