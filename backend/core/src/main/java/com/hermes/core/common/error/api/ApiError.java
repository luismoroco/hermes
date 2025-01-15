package com.hermes.core.common.error.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ApiError {
  private String type;
  private String message;
  private Timestamp timestamp;

  public ApiError(RuntimeException exc) {
    this.type = exc.getClass().getSimpleName();
    this.message = exc.getMessage();
    this.timestamp = new Timestamp(System.currentTimeMillis());
  }
}
