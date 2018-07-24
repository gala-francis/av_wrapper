package org.galatea.starter.domain;

import java.util.TreeMap;
import java.util.Date;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class StockData {

  private RequestMetaData requestMetaData;

  private TreeMap<Date, DayData> stockDataPoints;

  public StockData() {
    requestMetaData = new RequestMetaData();
    stockDataPoints = new TreeMap<Date, DayData>();
  }

}
