package org.galatea.starter.service;


import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import javax.servlet.http.HttpServletRequest;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import net.sf.aspect4log.Log;
import org.bson.Document;
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
  StockDataRepository stockDataRepository;

  private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_INSTANT
      .withLocale(Locale.US)
      .withZone(ZoneId.systemDefault());

  private static final int MAX_COMPACT_DAYS = 100;
  private static final String FULL = "full";
  private static final String COMPACT = "compact";

  // NYSE holidays for 2017 and 2018
  // ideally would find some API that provides these dates both historically
  // and into the future
  // used for determining valid trading days
  private static final List<LocalDate> holidays = Arrays.asList(
      LocalDate.of(2018,7,4),
      LocalDate.of(2018,5,28),
      LocalDate.of(2018,3,30),
      LocalDate.of(2018,2,19),
      LocalDate.of(2018,1,15),
      LocalDate.of(2018,1,1),
      LocalDate.of(2017,12,25),
      LocalDate.of(2017,11,23),
      LocalDate.of(2017,9,4),
      LocalDate.of(2017,7,4),
      LocalDate.of(2017,5,29),
      LocalDate.of(2017,4,14),
      LocalDate.of(2017,2,20),
      LocalDate.of(2017,1,16),
      LocalDate.of(2017,1,2));


  /**
   * Retrieve requested stock data for API client from either the Alpha Vantage API
   * or from Mongo database
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

    // if the method reaches this point, we know that the input is of valid format
    Document mongoData = stockDataRepository.getStockData(ticker);

    // if data exists in Mongo for the ticker
    if (!mongoData.isEmpty()) {
      // convert data returned from Mongo into StockData domain object
      StockData mongoStockData = StockData.fromMap((Map) mongoData.get("data"));

      // check if Mongo data covers required date range
      // if Mongo data has required date range, then truncate and return to API client
      if (checkMongoData(days, mongoStockData)) {
        return new StockResponse(generateRequestMetaData(ticker, days, request, requestDate),
                                 truncateStockData(mongoStockData, days));
      }
    }

    // when the function reaches this point, the data in Mongo does not have the required date range
    // therefore, Alpha Vantage must be queried
    // this call can throw TickerNotFoundException
    StockData avStockData = queryAlphaVantage(days, ticker);

    if (mongoData.isEmpty()) {
      // there was originally no data in Mongo, therefore add all data obtained from
      // Alpha Vantage to a new document in Mongo
      stockDataRepository.insertStockData(ticker, avStockData);
    } else {
      // there was some data in Mongo already, therefore update the existing document
      // with the data obtained from Alpha Vantage
      stockDataRepository.updateStockData(ticker, avStockData);
    }

    return new StockResponse(generateRequestMetaData(ticker, days, request, requestDate),
                             truncateStockData(avStockData, days));
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

  /**
   * Gets most recent valid trading day
   * If date passed in is a valid trading day, it will be returned
   *
   * @param date starting date to perform check on
   * @return the most recent trading day to the date passed in
   */
  private LocalDate getTradingDay(LocalDate date) {

    // this moves the current date back until it is a weekday
    if (date.getDayOfWeek() == DayOfWeek.SATURDAY) {
      date = date.minusDays(1);
    } else if (date.getDayOfWeek() == DayOfWeek.SUNDAY) {
      date = date.minusDays(2);
    }

    // if the current date is a holiday, this moves it back an appropriate
    // amount to the last weekday before the holiday
    if (holidays.contains(date)) {
      if (date.getDayOfWeek() == DayOfWeek.MONDAY) {
        date = date.minusDays(3);
      } else {
        date = date.minusDays(1);
      }
    }

    return date;
  }


  private boolean checkMongoData(int days, StockData mongoStockData) {
    // Need to check if the data stored in Mongo covers the required range
    LocalDate dateToCheck = LocalDate.now();

    /**
     * Might add in a check to see if first and last days exist in Mongo,
     * this will cover many cases where the data in Mongo is insufficient.
     * For now, perform iterative check over all days.
     */
    int checkedDays = 0;
    while (checkedDays < days) {

      // if current date isn't a valid trading day, goes backwards in time
      // until a valid trading day is found
      dateToCheck = getTradingDay(dateToCheck);

      // at this point, dateToCheck should be a valid trading day
      // check to see if dateToCheck exists in Mongo
      if (mongoStockData.getDataPoints().containsKey(dateToCheck)) {
        checkedDays += 1;
        dateToCheck = dateToCheck.minusDays(1);
      } else {
        // if the data is not in Mongo, break out of the loop
        break;
      }
    }

    return checkedDays == days;
  }


  /**
   * Generates RequestMetaData object
   *
   * @param ticker ticker that the API client requested data for
   * @param days number of days of data that the API client requested
   * @param request request object used to get request IP
   * @param requestDate date and time the request was initiated
   * @return RequestMetaData object containing all metadata related to request
   */
  private RequestMetaData generateRequestMetaData(String ticker, int days,
      HttpServletRequest request, Instant requestDate) {

    Instant endRequestDate = Instant.now();
    return  new RequestMetaData(
        ticker,
        days, formatter.format(requestDate),
        request.getRemoteAddr(),
        (endRequestDate.toEpochMilli() - requestDate.toEpochMilli()) / 1000f); // processing time

  }


  /**
   * Handles querying Alpha Vantage and returning valid data in a StockData object
   *
   * @param days number of days of data that the API client requested
   * @param ticker ticker that the API client requested data for
   * @return StockData object containing the data for the ticker past, contains all the data
   *         returned from Alpha Vantage, which may be logner than what the API client requested
   */
  private StockData queryAlphaVantage(int days, String ticker) {
    AVDailyDataResponse avDailyDataResponse = avService
        .getDailyData(ticker, (days > MAX_COMPACT_DAYS) ? FULL : COMPACT);

    if (avDailyDataResponse.getTimeSeriesDaily() == null)
      throw new TickerNotFoundException(ticker);

    return new StockData(avDailyDataResponse.getTimeSeriesDaily());
  }


}
