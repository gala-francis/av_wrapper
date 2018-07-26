package org.galatea.starter.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AVMetaData {

  @JsonProperty("1. Information")
  private String dataType;

  @JsonProperty("2. Symbol")
  private String symbol;

  @JsonProperty("3. Last Refreshed")
  private String lastRefreshed;

  @JsonProperty("4. Output Size")
  private String outputSize;

  @JsonProperty("5. Time Zone")
  private String timeZone;

}