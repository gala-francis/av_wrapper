package org.galatea.starter.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DayData {
  @JsonProperty("1. open")
  private float open;

  @JsonProperty("2. high")
  private float high;

  @JsonProperty("3. low")
  private float low;

  @JsonProperty("4. close")
  private float close;

  @JsonProperty("5. volume")
  private float volume;

  public DayData() { }

  public float getOpen()   { return this.open; }
  public float getHigh(  ) { return this.high; }
  public float getLow()    { return this.low; }
  public float getClose()  { return this.close; }
  public float getVolume() { return this.volume; }

  public void setOpen(float open)     { this.open = open; }
  public void setHigh(float high)     { this.high = high; }
  public void setLow(float low)       { this.low = low; }
  public void setClose(float close)   { this.close = close; }
  public void setVolume(float volume) { this.volume = volume; }

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
