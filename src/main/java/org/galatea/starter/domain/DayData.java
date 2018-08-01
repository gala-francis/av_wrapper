package org.galatea.starter.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@JsonIgnoreProperties(ignoreUnknown = true)
public class DayData {

  @JsonProperty("1. open")
  private Double open;

  @JsonProperty("2. high")
  private Double high;

  @JsonProperty("3. low")
  private Double low;

  @JsonProperty("4. close")
  private Double close;

  @JsonProperty("5. volume")
  private Double volume;

  public Map<String, Double> toMap() {
    Map<String, Double> dayDataMap = new TreeMap<>();
    dayDataMap.put("open", open);
    dayDataMap.put("high", high);
    dayDataMap.put("low", low);
    dayDataMap.put("close", close);
    dayDataMap.put("volume", volume);

    return dayDataMap;
  }

  public void setAll(Double open, Double high, Double low, Double close, Double volume) {
    this.open = open;
    this.high = high;
    this.low = low;
    this.close = close;
    this.volume = volume;
  }

}
