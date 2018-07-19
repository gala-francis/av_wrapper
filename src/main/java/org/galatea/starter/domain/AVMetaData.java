package org.galatea.starter.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AVMetaData {

  @JsonProperty("1. Information")
  private String information;

  @JsonProperty("2. Symbol")
  private String symbol;

  @JsonProperty("3. Last Refreshed")
  private String lastRefreshed;

  @JsonProperty("4. Output Size")
  private String outputSize;

  @JsonProperty("5. Time Zone")
  private String timeZone;

  public AVMetaData() { }

  public String getInformation() { return this.information; }
  public String getSymbol()        { return this.symbol; }
  public String getLastRefreshed() { return this.lastRefreshed; }
  public String getOutputSize()    { return this.outputSize; }
  public String getTimeZone()      { return this.timeZone; }

  public void setInformation(String information)     { this.information = information; }
  public void setSymbol(String symbol)               { this.symbol = symbol; }
  public void setLastRefreshed(String lastRefreshed) { this.lastRefreshed = lastRefreshed; }
  public void setOutputSize(String outputSize)       { this.outputSize = outputSize; }
  public void setTimeZone(String timeZone)           { this.timeZone = timeZone; }

  @Override
  public String toString() {
    return "AVMetaData{" +
        "information=" + information + ", " +
        "symbol=" + symbol + ", " +
        "lastRefreshed=" + lastRefreshed + ", " +
        "outputSize=" + outputSize + ", " +
        "timeZone=" + timeZone + "}";
  }

}