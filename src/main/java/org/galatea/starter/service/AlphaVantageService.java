package org.galatea.starter.service;

import java.util.HashMap;
import java.util.Map;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import net.sf.aspect4log.Log;
import org.galatea.starter.domain.AVDailyDataResponse;
import org.galatea.starter.domain.StockData;
import org.galatea.starter.entrypoint.exception.TickerNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@ToString
@EqualsAndHashCode
@Slf4j
@Log
@Service

public class AlphaVantageService {

  private static final String
      url = "https://www.alphavantage.co/query?function={function}&symbol={symbol}&outputsize={outputsize}&apikey={apikey}";
  private static final String apiKey = "59OJGP9MN11P9OM3";



  /**
   * Interfaces with Alpha Vantage API to retrieve stock data
   *
   * @param symbol ticker symbol to retrieve data for
   * @param size "compact" (100 days) or "full" (20 years) worth of data
   * @return StockData object with new data
   */
  public StockData getDailyData(String symbol, String size) {

    final String FUNCTION = "TIME_SERIES_DAILY";

    RestTemplate restTemplate = new RestTemplate();
    StockData stockData = new StockData();
    Map<String, Object> params = new HashMap<>();

    params.put("apikey", apiKey);
    params.put("function", FUNCTION);
    params.put("symbol", symbol);
    params.put("outputsize", size);

    AVDailyDataResponse avData = restTemplate.getForObject(url, AVDailyDataResponse.class, params);

    if (avData.getAvMetaData() == null) {
      throw new TickerNotFoundException(symbol);

    } else {
      stockData.setStockDataPoints(avData.getTimeSeriesDaily());
    }

    return stockData;
  }

}
