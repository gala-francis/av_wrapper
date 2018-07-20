package org.galatea.starter.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AVMetaData {

  @Getter
  @Setter
  @JsonProperty("1. Information")
  private String information;

  @Getter
  @Setter
  @JsonProperty("2. Symbol")
  private String symbol;

  @Getter
  @Setter
  @JsonProperty("3. Last Refreshed")
  private String lastRefreshed;

  @Getter
  @Setter
  @JsonProperty("4. Output Size")
  private String outputSize;

  @Getter
  @Setter
  @JsonProperty("5. Time Zone")
  private String timeZone;

  public AVMetaData() { }



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