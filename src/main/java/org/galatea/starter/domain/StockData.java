package org.galatea.starter.domain;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Data
@Slf4j
@RequiredArgsConstructor
public class StockData {

  @NonNull
  private final TreeMap<LocalDate, DayData> dataPoints;

  public Map<String, Map<String, Double>> toMap() {

    Map<String, Map<String, Double>> stockDataMap = new TreeMap<String, Map<String, Double>>();

    List<LocalDate> dateList = new ArrayList<LocalDate>(dataPoints.keySet());
    for (LocalDate key : dateList) {
      stockDataMap.put(key.toString(), dataPoints.get(key).toMap());
    }

    return stockDataMap;


  }

  public static StockData fromMap(Map<String, Map<String, Double>> data) {

    StockData stockData = new StockData(new TreeMap<>());

    for (String key : data.keySet()) {
      DayData dayData = new DayData();
      dayData.setAll(data.get(key).get("open"),
          data.get(key).get("high"),
          data.get(key).get("low"),
          data.get(key).get("close"),
          data.get(key).get("volume"));
      stockData.getDataPoints().put(LocalDate.parse(key), dayData);
    }

    return stockData;
  }

}
