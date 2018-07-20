package org.galatea.starter.service;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import net.sf.aspect4log.Log;
import org.galatea.starter.domain.StockData;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@ToString
@EqualsAndHashCode
@Slf4j
@Log
@Service
public class StockService {

  @NonNull
  AlphaVantageService avService;

  public StockService() {
    avService = new AlphaVantageService();
  }


  public String getData(String ticker, int days) {
    StockData avStockData = new StockData();
    avStockData.getStockDataMetaData().setDays(days);

    return avService.getDailyData(ticker, "compact", avStockData).toString();
  }
}
