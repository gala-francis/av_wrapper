package org.galatea.starter.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.AbstractMap.SimpleEntry;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
//  @JsonFormat
//      (shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")

  //  private LinkedHashMap<String, DayData> stockDataPoints;

  public StockData() {
    stockDataMetaData = new StockDataMetaData();
    stockDataPoints = new TreeMap<Date, DayData>(Collections.reverseOrder());
  }

  @Override
  public String toString() {
    return "StockData{" +
        "stockDataMetaData=" + stockDataMetaData + ", " +
        "stockDataPoints=" + stockDataPoints + "}";
  }


}
