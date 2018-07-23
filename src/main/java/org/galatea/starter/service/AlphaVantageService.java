package org.galatea.starter.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.HashMap;
import java.util.Map;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import net.sf.aspect4log.Log;
import org.galatea.starter.domain.AVDailyDataResponse;
import org.galatea.starter.domain.StockData;
import org.galatea.starter.domain.StockDataMetaData;
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
      REQUEST = "https://www.alphavantage.co/query?function={function}&symbol={symbol}&outputsize={outputsize}&apikey={apikey}";
  private static final String API_KEY = "59OJGP9MN11P9OM3";



  public StockData getDailyData(String symbol, String size, StockData stockData) {
    RestTemplate restTemplate = new RestTemplate();
    Map<String, Object> params = new HashMap<>();
    final String FUNCTION = "TIME_SERIES_DAILY";


    params.put("apikey", API_KEY);
    params.put("function", FUNCTION);
    params.put("symbol", symbol);
    params.put("outputsize", size);

    AVDailyDataResponse avData;


    avData = restTemplate.getForObject(REQUEST, AVDailyDataResponse.class, params);

    if (avData.getTimeSeriesDaily() == null) {
      stockData.getStockDataMetaData().setInformation("Error: Ticker does not exist");
      log.info(stockData.toString());
      log.info("avData is null!!");
    } else {
      stockData.setStockDataPoints(avData.getTimeSeriesDaily());
      stockData.getStockDataMetaData().setInformation("Success");
      log.info("avData is good");
      log.info(avData.toString());
      log.info(stockData.toString());
    }

    return stockData;
  }

}
