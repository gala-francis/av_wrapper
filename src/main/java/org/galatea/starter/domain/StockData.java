package org.galatea.starter.domain;

import java.util.Map;
import lombok.Getter;
import lombok.Setter;

public class StockData {

  @Getter
  @Setter
  private StockDataMetaData stockDataMetaData;

  @Getter
  @Setter
  private Map<String, DayData> stockDataPoints;

  public StockData() { stockDataMetaData = new StockDataMetaData(); }

  @Override
  public String toString() {
    return "StockData{" +
        "stockDataMetaData=" + stockDataMetaData + ", " +
        "stockDataPoints=" + stockDataPoints + "}";
  }


}
