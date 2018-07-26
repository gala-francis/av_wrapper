package org.galatea.starter.service;


import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import net.sf.aspect4log.Log;
import org.galatea.starter.domain.DayData;
import org.galatea.starter.domain.StockData;
import org.galatea.starter.domain.RequestMetaData;
import org.galatea.starter.entrypoint.exception.InvalidDaysException;
import org.galatea.starter.entrypoint.exception.InvalidTickerException;
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

  /**
   * Constructor that initializes AlphaVantageService
   */
  public StockService() {
    avService = new AlphaVantageService();
  }


  /**
   * Retrieve requested stock data for API client
   *
   * @param ticker ticker API client requested data for
   * @param days number of days API client requested data for
   * @param request request metadata forwarded from REST Controller
   * @return ticker data that API client requested
   */
  public StockData getData(String ticker, int days, HttpServletRequest request) {

    Instant requestDate = Instant.now();
//    DateTimeFormatter formatter =
//        DateTimeFormatter.ofLocalizedDateTime( FormatStyle. )
//            .withLocale( Locale.US )
//            .withZone( ZoneId.systemDefault() );

      DateTimeFormatter formatter = DateTimeFormatter.ISO_INSTANT
          .withLocale(Locale.US)
          .withZone(ZoneId.systemDefault());


    StockData avStockData = new StockData();
    StockData clientStockData = new StockData();
    RequestMetaData requestMetaData = new RequestMetaData();



    clientStockData.getRequestMetaData().setRequestIP(request.getRemoteAddr());
    clientStockData.getRequestMetaData().setTicker(ticker);
    clientStockData.getRequestMetaData().setRequestDate(formatter.format(requestDate));

    if (!validateTicker(ticker)) {

      throw new InvalidTickerException(ticker);

    } else if (!validateDays(days)) {

      throw new InvalidDaysException();



    } else {

      avStockData = avService.getDailyData(ticker, (days > 100) ? "full":"compact");

      clientStockData.getRequestMetaData().setDays(days);

      Set dateSet = avStockData.getStockDataPoints().descendingKeySet();
      Iterator<LocalDate> dateSetIterator = dateSet.iterator();

      LocalDate key;
      DayData value;

      for (int x = 0; x < days; x++) {
        key = dateSetIterator.next();
        value = avStockData.getStockDataPoints().get(key);
        clientStockData.getStockDataPoints().put(key, value);
      }
    }

    Instant endRequestDate = Instant.now();
    clientStockData.getRequestMetaData().setProcessingTimeSec(
        (endRequestDate.toEpochMilli() - requestDate.toEpochMilli()) /1000f);

    return clientStockData;

  }

  /**
   * Checks if a ticker is valid, in that in consists of only letters
   * and consists of between one and five characters
   *
   * @param ticker ticker symbol to validate
   *
   * @return true/false to indicate if the ticker is valid
   */
  private boolean validateTicker(String ticker) {
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
  private boolean validateDays(int days) {
    return (days > 0);

  }

}
