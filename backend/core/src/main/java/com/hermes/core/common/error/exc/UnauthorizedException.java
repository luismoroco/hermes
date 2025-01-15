package com.hermes.core.common.error.exc;

import com.hermes.core.common.error.HermesException;

public class UnauthorizedException extends HermesException {
  public UnauthorizedException(String message) {
    super(message);
  }
}
