package org.galatea.starter.service;


import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TreeMap;
import javax.servlet.http.HttpServletRequest;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import net.sf.aspect4log.Log;
import org.galatea.starter.domain.AVDailyDataResponse;
import org.galatea.starter.domain.DayData;
import org.galatea.starter.domain.StockData;
import org.galatea.starter.domain.RequestMetaData;
import org.galatea.starter.domain.StockResponse;
import org.galatea.starter.entrypoint.exception.InvalidDaysException;
import org.galatea.starter.entrypoint.exception.InvalidTickerException;
import org.galatea.starter.entrypoint.exception.TickerNotFoundException;
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

  @NonNull
  MongoService mongoService;

  private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_INSTANT
      .withLocale(Locale.US)
      .withZone(ZoneId.systemDefault());

  private static final int MAX_COMPACT_DAYS = 100;
  private static final String FULL = "full";
  private static final String COMPACT = "compact";

  /**
   * Retrieve requested stock data for API client
   *
   * @param ticker ticker API client requested data for
   * @param days number of days API client requested data for
   * @param request request metadata forwarded from REST Controller
   * @return ticker data that API client requested
   */
  public StockResponse getData(final String ticker, final int days, final HttpServletRequest request) {
    Instant requestDate = Instant.now();

    if (!validateTicker(ticker)) {

      throw new InvalidTickerException();

    } else if (!validateDays(days)) {

      throw new InvalidDaysException();

    }

    // If the method reaches this point, we know that the input is of valid format

    AVDailyDataResponse avDailyDataResponse = avService.getDailyData(ticker, (days > MAX_COMPACT_DAYS) ? FULL:COMPACT);

    if (avDailyDataResponse.getTimeSeriesDaily() == null) {

      throw new TickerNotFoundException(ticker);

    }

    StockData avStockData = new StockData(avDailyDataResponse.getTimeSeriesDaily());

    mongoService.putStockData(ticker, avStockData);

    StockData truncatedStockData = truncateStockData(avStockData, days);
    Instant endRequestDate = Instant.now();

    RequestMetaData requestMetaData = new RequestMetaData(
        ticker,
        days, formatter.format(requestDate),
        request.getRemoteAddr(),
        (endRequestDate.toEpochMilli() - requestDate.toEpochMilli()) / 1000f); // processing time


    return new StockResponse(requestMetaData, truncatedStockData);

  }

  /**
   *
   * @param stockData StockData object to truncate
   * @param days number of days to truncate to
   * @return new StockData object that contains specified
   *             number of days of data
   */
  private StockData truncateStockData(final StockData stockData, final int days) {
    StockData truncatedStockData = new StockData(new TreeMap<>());

    List<LocalDate> dateList =
        new ArrayList<LocalDate>(stockData.getDataPoints().descendingKeySet());

    LocalDate date;
    DayData data;

    for (int i = 0; i < days; i++) {
      date = dateList.get(i);
      data = stockData.getDataPoints().get(date);
      truncatedStockData.getDataPoints().put(date, data);
    }

    return truncatedStockData;

  }

  /**
   * Checks if a ticker is valid, in that in consists of only letters
   * and consists of between one and five characters
   *
   * @param ticker ticker symbol to validate
   *
   * @return true/false to indicate if the ticker is valid
   */
  private boolean validateTicker(final String ticker) {
    int len = ticker.length();

    return (len >= 1) && (len <= 5) && ticker.matches("[a-zA-Z]+");
  }

  /**
   * Ensures that the days string is valid, in that it represents an
   * integer greater than zero
   *
   * @param days string representing number of days of date the API client
   *             requested
   * @return true/false to indicate if the days string valid
   */
  private boolean validateDays(final int days) {
    return (days > 0);

  }

}
