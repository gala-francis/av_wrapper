package org.galatea.starter.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class RequestMetaData {

  private String ticker;

  private int days;

  private String requestDate;

  private String requestIP;

  private float processingTimeSec;

}
