package org.galatea.starter;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.extern.slf4j.Slf4j;
import org.galatea.starter.service.StockDataRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class StockDataRepositoryConfig {

  @Value("${mongo.dailyuri}")
  private String dailyUri;


  @Value("${mongo.username}")
  private String username;

  @Value("${mongo.password}")
  private String password;

  @Bean
  public MongoClientURI mongoClientURI() {
    return new MongoClientURI(String.format(dailyUri, username, password));
  }

  @Bean
  public MongoClient mongoClient(final MongoClientURI mongoClientURI) {
    return new MongoClient(mongoClientURI);
  }

  @Bean
  public MongoCollection mongoCollection(final MongoClient mongoClient,
      final MongoClientURI mongoClientURI) {
    MongoDatabase db = mongoClient.getDatabase(mongoClientURI.getDatabase());
    return db.getCollection("dailyStockData");
  }

  @Bean
  public StockDataRepository stockDataRepository() {
    return new StockDataRepository(dailyUri, username, password,
        mongoCollection(mongoClient(mongoClientURI()), mongoClientURI()));
  }


}
