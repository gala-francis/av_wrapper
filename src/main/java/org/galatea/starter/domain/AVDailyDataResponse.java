package org.galatea.starter.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.AbstractMap.SimpleEntry;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AVDailyDataResponse {


  @Getter
  @Setter
  @JsonProperty("Meta Data")
  private AVMetaData avMetaData;

  @Getter
  @Setter
  @JsonProperty("Time Series (Daily)")
  private TreeMap<Date, DayData> timeSeriesDaily;
//  private LinkedHashMap<String, DayData> timeSeriesDaily;
  //private List<Map<String, DayData>> timeSeriesDaily;

  public AVDailyDataResponse() { }

  @Override
  public String toString() {
    return "AVDailyAll{" +
        "avMetaData=" + avMetaData + ", " +
        "timeSeriesDaily=" + timeSeriesDaily + "}";
  }
}
