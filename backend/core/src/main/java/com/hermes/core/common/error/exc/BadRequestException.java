package com.hermes.core.common.error.exc;

import com.hermes.core.common.error.HermesException;

public class BadRequestException extends HermesException {
  public BadRequestException(String message) {
    super(message);
  }
}
