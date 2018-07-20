package org.galatea.starter.domain;

public class StockDataMetaData {

  private String ticker;

  private int days;

  private String requestDate;

  private String requestIP;

  private float processingTime;

  public StockDataMetaData() { }

  public String getTicker() {
    return ticker;
  }

  public void setTicker(String ticker) {
    this.ticker = ticker;
  }

  public int getDays() {
    return days;
  }

  public void setDays(int days) {
    this.days = days;
  }

  public String getRequestDate() {
    return requestDate;
  }

  public void setRequestDate(String requestDate) {
    this.requestDate = requestDate;
  }

  public String getRequestIP() {
    return requestIP;
  }

  public void setRequestIP(String requestIP) {
    this.requestIP = requestIP;
  }

  public float getProcessingTime() {
    return processingTime;
  }

  public void setProcessingTime(float processingTime) {
    this.processingTime = processingTime;
  }

  @Override
  public String toString() {
    return "StockDataMetaData{" +
        "ticker=" + ticker + ", " +
        "days=" + days + ", " +
        "requestDate=" + requestDate + ", " +
        "requestIP=" + requestIP + ", " +
        "processingTime=" + processingTime + "}";
  }


}
