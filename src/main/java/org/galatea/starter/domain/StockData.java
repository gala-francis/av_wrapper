package org.galatea.starter.domain;

import java.util.Map;
import lombok.Getter;
import lombok.Setter;

public class StockData {

  //@Setter
  //@Getter
  private StockDataMetaData stockDataMetaData;

  private Map<String, DayData> stockDataPoints;

  public StockData() { stockDataMetaData = new StockDataMetaData(); }

  public Map<String, DayData> getStockDataPoints() {
    return stockDataPoints;
  }

  public void setStockDataPoints(
      Map<String, DayData> stockDataPoints) {
    this.stockDataPoints = stockDataPoints;
  }



  public StockDataMetaData getStockDataMetaData() {
    return stockDataMetaData;
  }

  public void setStockDataMetaData(StockDataMetaData stockDataMetaData) {
    this.stockDataMetaData = stockDataMetaData;
  }

  @Override
  public String toString() {
    return "StockData{" +
        "stockDataMetaData=" + stockDataMetaData + ", " +
        "stockDataPoints=" + stockDataPoints + "}";
  }


}
