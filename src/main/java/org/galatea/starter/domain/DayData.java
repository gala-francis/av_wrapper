package org.galatea.starter.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DayData {
  @Getter
  @Setter
  @JsonProperty("1. open")
  private float open;

  @Getter
  @Setter
  @JsonProperty("2. high")
  private float high;

  @Getter
  @Setter
  @JsonProperty("3. low")
  private float low;

  @Getter
  @Setter
  @JsonProperty("4. close")
  private float close;

  @Getter
  @Setter
  @JsonProperty("5. volume")
  private float volume;

  public DayData() { }



  @Override
  public String toString() {
    return "DayData{" +
        "open=" + open + ", " +
        "high=" + high + ", " +
        "low=" + low+ ", " +
        "close=" + close+ ", " +
        "volume=" + volume+ "}";
  }


}
