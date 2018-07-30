package org.galatea.starter.domain;

import java.time.LocalDate;
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

  public Map<String, Map<String, Float>> toMap() {

    Map<String, Map<String, Float>> stockDataMap = new TreeMap<String, Map<String, Float>>();

    List<LocalDate> dateList = new ArrayList<LocalDate>(dataPoints.keySet());
    for (LocalDate key : dateList) {
      stockDataMap.put(key.toString(), dataPoints.get(key).toMap());
    }

    return stockDataMap;


  }

}
