package org.galatea.starter.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@RequiredArgsConstructor
public class RequestMetaData {

  @NonNull
  private String ticker;

  @NonNull
  private int days;

  @NonNull
  private String requestDate;

  @NonNull
  private String requestIP;

  @NonNull
  private float processingTimeSec;

}
