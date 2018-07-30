package org.galatea.starter.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
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

  public Map<String, Float> toMap() {
    Map<String, Float> dayDataMap = new TreeMap<>();
    dayDataMap.put("open", open);
    dayDataMap.put("high", high);
    dayDataMap.put("low", low);
    dayDataMap.put("close", close);
    dayDataMap.put("volume", volume);

    return dayDataMap;
  }

}
