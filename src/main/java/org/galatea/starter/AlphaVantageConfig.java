package org.galatea.starter;

import lombok.extern.slf4j.Slf4j;
import org.galatea.starter.service.AlphaVantageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Config for method-level validation (such as validating method input params)
 */
@Configuration

@Slf4j
public class AlphaVantageConfig {

  @Value("${alphavantage.dailyurl}")
  private String dailyUrl;

  @Value("${alphavantage.apikey}")
  private String apiKey;


  @Bean
  public AlphaVantageService alphaVantageService() {
    return new AlphaVantageService(dailyUrl, apiKey);
  }
}
