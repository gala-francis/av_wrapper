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

  @NonNull
  private MongoClientURI mongoClientURI;

  @NonNull
  private MongoClient client;



  public MongoService(String dailyUri, String username, String password) {
    this.dailyUri = dailyUri;
    this.username = username;
    this.password = password;

    mongoClientURI =
        new MongoClientURI(String.format(dailyUri, username, password));
//    MongoDatabase db = client.getDatabase(mongoClientURI.getDatabase());
//
//    mongoDb = db.getCollection("test");

  }



  public void connectionTest() {

    List<Document> seedData = new ArrayList<Document>();

    seedData.add(new Document("decade", "1970s")
        .append("artist", "Debby Boone")
        .append("song", "You Light Up My Life")
        .append("weeksAtOne", 10)
    );

    seedData.add(new Document("decade", "1980s")
        .append("artist", "Olivia Newton-John")
        .append("song", "Physical")
        .append("weeksAtOne", 10)
    );

    seedData.add(new Document("decade", "1990s")
        .append("artist", "Mariah Carey")
        .append("song", "One Sweet Day")
        .append("weeksAtOne", 16)
    );

//    MongoClientURI uri =
//        new MongoClientURI(String.format(dailyUri, username, password));
//
    MongoClient client = new MongoClient(mongoClientURI);
    MongoDatabase db = client.getDatabase(mongoClientURI.getDatabase());
    MongoCollection<Document> test = db.getCollection("test");

    test.insertMany(seedData);

    test.drop();

    client.close();

  }

  public Document getStockData(String ticker) {
    MongoClient client = new MongoClient(mongoClientURI);
    MongoDatabase db = client.getDatabase(mongoClientURI.getDatabase());
    MongoCollection<Document> dailyStockData = db.getCollection("dailyStockData");



    Document doc = dailyStockData.find(eq("ticker", ticker)).first();
    if (doc != null) {
      log.info(doc.toJson());
    } else {
      log.info("No matching document found");
    }

    client.close();

    return doc;

  }

public Document putStockData(String ticker, StockData stockData) {
  MongoClient client = new MongoClient(mongoClientURI);
  MongoDatabase db = client.getDatabase(mongoClientURI.getDatabase());
  MongoCollection<Document> dailyStockData = db.getCollection("dailyStockData");

  Document doc = getStockData(ticker);

//  Map<LocalDate, DayData> stockDataMap = stockData.getDataPoints();
//  Map<String, DayData> stockDataStringMap = new TreeMap<>();
//

//  List<LocalDate> dateList = new ArrayList<LocalDate>(stockDataMap.keySet());
//
//  for (LocalDate date : dateList) {
//    stockDataStringMap.put(date.toString(),  new BasicDBObject(stockDataMap.get(date)));
//  }

  if (doc == null) {
//    doc = new Document("ticker", ticker).append("data", new BasicDBObject(stockData.getDataPoints()));
    doc = new Document("ticker", ticker).append("data", stockData.toMap());
  } else {
    doc.put("data", stockData.toMap());
  }



  dailyStockData.insertOne(doc);

  return doc;

}





}
