package org.galatea.starter.service;

import static com.mongodb.client.model.Filters.eq;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.time.LocalDate;
import java.util.Map;
import java.util.TreeMap;
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
public class MongoService {

  @NonNull
  private final String dailyUri;

  @NonNull
  private final String username;

  @NonNull
  private final String password;

  /**
   * Queries Mongo dailyStockData database for a given ticker
   *
   * @param ticker ticker to query database for
   * @return document corresponding to ticker (null if it doesn't exist)
   */
  public Document getStockData(String ticker) {
    MongoClientURI mongoClientURI =
        new MongoClientURI(String.format(dailyUri, username, password));
    MongoClient client = new MongoClient(mongoClientURI);
    MongoDatabase db = client.getDatabase(mongoClientURI.getDatabase());
    MongoCollection<Document> dailyStockData = db.getCollection("dailyStockData");

    Document doc = dailyStockData.find(eq("ticker", ticker)).first();

    client.close();

    return doc;

  }

  /**
   * Creates new document for ticker with data in the dailyStockData database
   *
   * @param ticker ticker of new data
   * @param stockData new data for ticker
   */
  public void putStockData(String ticker, StockData stockData) {
    MongoClientURI mongoClientURI =
        new MongoClientURI(String.format(dailyUri, username, password));
    MongoClient client = new MongoClient(mongoClientURI);
    MongoDatabase db = client.getDatabase(mongoClientURI.getDatabase());
    MongoCollection<Document> dailyStockData = db.getCollection("dailyStockData");

    Document doc = getStockData(ticker);

    if (doc == null) {
      doc = new Document("ticker", ticker).append("data", stockData.toMap());
    } else {
      doc.put("data", stockData.toMap());
    }

    dailyStockData.insertOne(doc);
    client.close();

  }

  /**
   * Updates existing document corresponding to ticker with new data
   *
   * @param ticker ticker to update
   * @param stockData new data to add to document
   */
  public void updateStockData(String ticker, StockData stockData) {
    MongoClientURI mongoClientURI =
        new MongoClientURI(String.format(dailyUri, username, password));
    MongoClient client = new MongoClient(mongoClientURI);
    MongoDatabase db = client.getDatabase(mongoClientURI.getDatabase());
    MongoCollection<Document> dailyStockData = db.getCollection("dailyStockData");

    Document doc = getStockData(ticker);

    if (doc == null) {
      // if document doesn't actually exist, create new document with the data
      // another option would be to throw an exception here and only allow users
      // to update documents that actually exist
      putStockData(ticker, stockData);

    } else {
      StockData oldStockData = new StockData(new TreeMap<>());
      oldStockData.fromMap((Map)doc.get("data"));
      for (LocalDate newDate : stockData.getDataPoints().keySet()) {
        if (!oldStockData.getDataPoints().containsKey(newDate)) {
          oldStockData.getDataPoints().put(newDate, stockData.getDataPoints().get(newDate));
        }
      }

      dailyStockData.updateOne(eq("ticker", ticker),
          new Document("$set", new Document("data", oldStockData.toMap())));
    }

    client.close();

  }




}
