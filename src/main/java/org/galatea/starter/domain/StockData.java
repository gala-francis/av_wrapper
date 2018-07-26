package org.galatea.starter.domain;

import java.time.LocalDate;
import java.util.TreeMap;

import lombok.Data;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;


@Data
@Slf4j
public class StockData {

  @NonNull
  private RequestMetaData requestMetaData;

  @NonNull
  private TreeMap<LocalDate, DayData> stockDataPoints;

  public StockData() {
    requestMetaData = new RequestMetaData();
    stockDataPoints = new TreeMap<>();
  }

}
