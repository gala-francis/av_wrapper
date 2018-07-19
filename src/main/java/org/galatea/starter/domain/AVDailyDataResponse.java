package org.galatea.starter.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AVDailyDataResponse {

  @JsonProperty("Meta Data")
  private AVMetaData avMetaData;

  @JsonProperty("Time Series (Daily)")
  private Map<String, DayData> timeSeriesDaily;

  public AVDailyDataResponse() { }

  public AVMetaData getAVMetaData() { return this.avMetaData; }
  public Map<String, DayData> getTimeSeriesDaily() { return this.timeSeriesDaily; }

  public void setAVMetaData(AVMetaData avMetaData) { this.avMetaData = avMetaData; }
  public void setTimeSeriesDaily(Map<String, DayData> timeSeriesDaily) { this.timeSeriesDaily = timeSeriesDaily; }

  @Override
  public String toString() {
    return "AVDailyAll{" +
        "avMetaData=" + avMetaData + ", " +
        "timeSeriesDaily=" + timeSeriesDaily + "}";
  }
}
