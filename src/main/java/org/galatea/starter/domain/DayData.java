package org.galatea.starter.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.Entity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class DayData {

  @JsonProperty("1. open")
  private float open;

  @JsonProperty("2. high")
  private float high;

  @JsonProperty("3. low")
  private float low;

  @JsonProperty("4. close")
  private float close;

  @JsonProperty("5. volume")
  private float volume;

}
