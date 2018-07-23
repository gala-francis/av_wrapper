package org.galatea.starter.domain;

import lombok.Getter;
import lombok.Setter;

public class StockDataMetaData {

  @Getter
  @Setter
  private String ticker;

  @Getter
  @Setter
  private int days;

  @Getter
  @Setter
  private String requestDate;

  @Getter
  @Setter
  private String requestIP;

  @Getter
  @Setter
  private float processingTimeSec;

  @Getter
  @Setter
  private String information;

  public StockDataMetaData() { }


  @Override
  public String toString() {
    return "StockDataMetaData{" +
        "ticker=" + ticker + ", " +
        "days=" + days + ", " +
        "requestDate=" + requestDate + ", " +
        "requestIP=" + requestIP + ", " +
        "processingTimeSec=" + processingTimeSec + "," +
        "information=" + information + "}";
  }


}
