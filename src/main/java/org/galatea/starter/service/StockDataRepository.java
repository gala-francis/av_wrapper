package org.galatea.starter.service;

import static com.mongodb.client.model.Filters.eq;
import com.mongodb.client.MongoCollection;
import java.time.LocalDate;
import java.util.Map;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import net.sf.aspect4log.Log;
import org.bson.Document;
import org.galatea.starter.domain.StockData;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@ToString
@EqualsAndHashCode
@Slf4j
@Log
@Service
public class StockDataRepository {

  @NonNull
  private final String dailyUri;

  @NonNull
  private final String username;

  @NonNull
  private final String password;

  @NonNull
  MongoCollection<Document> dailyStockData;

  /**
   * Queries Mongo dailyStockData database for a given ticker
   *
   * @param ticker ticker to query database for
   * @return document corresponding to ticker (null if it doesn't exist)
   */
  public Document getStockData(String ticker) {

    Document doc = dailyStockData.find(eq("ticker", ticker)).first();

    return (doc == null) ? new Document() : doc;

  }

  /**
   * Creates new document for ticker with data in the dailyStockData database
   *
   * @param ticker ticker of new data
   * @param stockData new data for ticker
   */
  public void insertStockData(String ticker, StockData stockData) {

    dailyStockData.insertOne(new Document("ticker", ticker).append("data", stockData.toMap()));

  }

  /**
   * Updates existing document corresponding to ticker with new data
   *
   * @param ticker ticker to update
   * @param stockData new data to add to document
   */
  public void updateStockData(String ticker, StockData stockData) {

      StockData oldStockData = StockData.fromMap((Map)getStockData(ticker).get("data"));

      for (LocalDate newDate : stockData.getDataPoints().keySet()) {
        if (!oldStockData.getDataPoints().containsKey(newDate)) {
          oldStockData.getDataPoints().put(newDate, stockData.getDataPoints().get(newDate));
        }
      }

      dailyStockData.updateOne(eq("ticker", ticker),
          new Document("$set", new Document("data", oldStockData.toMap())));
  }
}
