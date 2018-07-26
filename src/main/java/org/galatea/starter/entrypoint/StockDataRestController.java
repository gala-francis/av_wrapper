package org.galatea.starter.entrypoint;

import javax.servlet.http.HttpServletRequest;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import net.sf.aspect4log.Log;
import net.sf.aspect4log.Log.Level;

import org.galatea.starter.service.StockService;
import org.galatea.starter.domain.StockData;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * REST Controller that generates and listens to http endpoints that allow the
 * API client to request stock data for a ticker for any number of days
 */
@RequiredArgsConstructor
@ToString
@EqualsAndHashCode
@Slf4j
@Log(enterLevel = Level.INFO, exitLevel = Level.INFO)
@RestController
public class StockDataRestController extends BaseRestController{

  @NonNull
  StockService stockService;

  /**
   * Send the received ticker and days to StockService for processing
   * and return data back to user
   */
  // @GetMapping to link http GET request to this method
  // @RequestParam to take parameter from the url
  @GetMapping(value = "${webservice.stockpath}", produces = {MediaType.APPLICATION_JSON_VALUE})
  public StockData stockDataEndpoint(@RequestParam(value = "ticker") String ticker,
      @RequestParam(value = "days") int days,
      @RequestParam(value = "requestId", required = false) String requestId,
      HttpServletRequest request) {
    processRequestId(requestId);
    return stockService.getData(ticker, days, request);
  }
}
