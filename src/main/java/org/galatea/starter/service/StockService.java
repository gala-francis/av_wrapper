package org.galatea.starter.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
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


  public StockData getData(String ticker, String days, HttpServletRequest request) {

    Date requestDate = new Date();
    DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");

    StockData avStockData = new StockData();
    StockData clientStockData = new StockData();


    clientStockData.getStockDataMetaData().setRequestIP(request.getRemoteAddr());
    clientStockData.getStockDataMetaData().setTicker(ticker);
    clientStockData.getStockDataMetaData().setRequestDate(dateFormat.format(requestDate));

    if (!validateTicker(ticker)) {

      clientStockData.getStockDataMetaData().setInformation("Error: Invalid Ticker");

    } else if (!validateDays(days)) {

      clientStockData.getStockDataMetaData().setInformation("Error: Invalid Days");


    } else {

      avStockData = avService.getDailyData(ticker, (Integer.parseInt(days) > 100) ? "full":"compact", avStockData);

      log.info(avStockData.toString());

      clientStockData.getStockDataMetaData().setInformation(
          avStockData.getStockDataMetaData().getInformation());

      clientStockData.getStockDataMetaData().setDays(Integer.parseInt(days));

      if (!(avStockData.getStockDataMetaData().getInformation().contains("Error"))) {

        Set dateSet = avStockData.getStockDataPoints().descendingKeySet();
        Iterator<Date> dateSetIterator = dateSet.iterator();

        Date key;
        DayData value;

        for (int x = 0; x < Integer.parseInt(days); x++) {
          key = dateSetIterator.next();
          value = avStockData.getStockDataPoints().get(key);
          clientStockData.getStockDataPoints().put(key, value);
        }
      }
    }

    Date endRequestDate = new Date();
    clientStockData.getStockDataMetaData().setProcessingTimeSec(
        (endRequestDate.getTime() - requestDate.getTime())/1000f);

    return clientStockData;

  }

  private boolean validateTicker(String ticker) {
    int len = ticker.length();

    return (len >= 1) && (len <= 5) && ticker.matches("[a-zA-Z]+");
  }

  private boolean validateDays(String days) {
    return days.matches("^[1-9]\\d*$");

  }

}
