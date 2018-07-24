package org.galatea.starter.entrypoint.exception;

public class InvalidTickerException extends RuntimeException {

  public InvalidTickerException(String ticker) {
    super("Ticker " + ticker + " is invalid. Tickers must be between one and five"
        + " characters, and only contain letters.");
  }
}
