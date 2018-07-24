package org.galatea.starter.domain;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class RequestMetaData {

  private String ticker;

  private int days;

  private String requestDate;

  private String requestIP;

  private float processingTimeSec;

}
