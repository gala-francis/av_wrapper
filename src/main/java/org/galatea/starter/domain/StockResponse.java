package org.galatea.starter.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@RequiredArgsConstructor
public class StockResponse {

  @NonNull
  private final RequestMetaData requestMetaData;

  @NonNull
  private final StockData stockData;

}
