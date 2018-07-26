package org.galatea.starter.domain;

import java.time.LocalDate;
import java.util.TreeMap;
import java.util.Date;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDateTime;

@Data
@Slf4j
public class StockData {

  private RequestMetaData requestMetaData;

  private TreeMap<LocalDate, DayData> stockDataPoints;

  public StockData() {
    requestMetaData = new RequestMetaData();
    stockDataPoints = new TreeMap<LocalDate, DayData>();
  }

}
