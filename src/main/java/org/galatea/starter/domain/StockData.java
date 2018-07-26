package org.galatea.starter.domain;

import java.time.LocalDate;
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

}
