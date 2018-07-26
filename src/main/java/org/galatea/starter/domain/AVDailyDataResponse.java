package org.galatea.starter.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.TreeMap;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AVDailyDataResponse {

  @JsonProperty("Meta Data")
  private AVMetaData avMetaData;

  @JsonProperty("Time Series (Daily)")
  private TreeMap<LocalDate, DayData> timeSeriesDaily;


}
