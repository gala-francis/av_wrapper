package org.galatea.starter;

import lombok.extern.slf4j.Slf4j;
import org.galatea.starter.service.MongoService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class MongoConfig {

  @Value("${mongo.dailyuri}")
  private String dailyUri;

  @Value("${mongo.username}")
  private String username;

  @Value("${mongo.password}")
  private String password;

  @Bean
  public MongoService mongoService() {
    return new MongoService(dailyUri, username, password);
  }


}
