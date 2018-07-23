package org.galatea.starter.domain;

import java.util.TreeMap;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

public class StockData {

  @Getter
  @Setter
  private StockDataMetaData stockDataMetaData;

  @Getter
  @Setter
  private TreeMap<Date, DayData> stockDataPoints;


  public StockData() {
    stockDataMetaData = new StockDataMetaData();
    stockDataPoints = new TreeMap<Date, DayData>();
  }

  @Override
  public String toString() {
    return "StockData{" +
        "stockDataMetaData=" + stockDataMetaData + ", " +
        "stockDataPoints=" + stockDataPoints + "}";
  }


}
