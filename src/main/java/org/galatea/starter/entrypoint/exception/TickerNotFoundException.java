package org.galatea.starter.entrypoint.exception;

public class TickerNotFoundException extends RuntimeException {

  public TickerNotFoundException(String ticker) {
    super("Ticker " + ticker + " not found.");
  }

}
