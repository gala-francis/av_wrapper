package org.galatea.starter.service;

import java.util.HashMap;
import java.util.Map;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import net.sf.aspect4log.Log;
import org.galatea.starter.domain.AVDailyDataResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@ToString
@EqualsAndHashCode
@Slf4j
@Log
@Service
public class AlphaVantageService {

  @NonNull
  private final String dailyUrl;

  @NonNull
  private final String apiKey;

  private static final String FUNCTION_DAILY_DATA = "TIME_SERIES_DAILY";


  /**
   * Interfaces with Alpha Vantage API to retrieve stock data
   *
   * @param symbol ticker symbol to retrieve data for
   * @param size "compact" (100 days) or "full" (20 years) worth of data
   * @return StockData object with new data
   */
  public AVDailyDataResponse getDailyData(final String symbol, final String size) {

    RestTemplate restTemplate = new RestTemplate();
    Map<String, Object> params = new HashMap<>();

    params.put("apikey", apiKey);
    params.put("function", FUNCTION_DAILY_DATA);
    params.put("symbol", symbol);
    params.put("outputsize", size);

    AVDailyDataResponse avData = restTemplate.getForObject(dailyUrl, AVDailyDataResponse.class, params);

    return avData;
  }

}
