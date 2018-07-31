package org.galatea.starter.service;

import static com.mongodb.client.model.Filters.eq;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import java.util.Map;
import java.util.TreeMap;
import javax.print.Doc;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import net.sf.aspect4log.Log;
import org.bson.Document;
import org.galatea.starter.domain.DayData;
import org.galatea.starter.domain.StockData;
import org.galatea.starter.entrypoint.exception.EntityNotFoundException;
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

  public void updateStockData(String ticker, StockData stockData) {
    MongoClientURI mongoClientURI =
        new MongoClientURI(String.format(dailyUri, username, password));
    MongoClient client = new MongoClient(mongoClientURI);
    MongoDatabase db = client.getDatabase(mongoClientURI.getDatabase());
    MongoCollection<Document> dailyStockData = db.getCollection("dailyStockData");

    Document doc = getStockData(ticker);

    if (doc == null) {
//       if document doesn't exist and tried to update, what should the behavior be?
//      doc = new Document("ticker", ticker).append("data", stockData.toMap());
//        throw EntityNotFoundException( );

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
