package com.hermes.core.common.error.exc;

import com.hermes.core.common.error.HermesException;

public class NotFoundException extends HermesException {
  public NotFoundException(String message) {
    super(message);
  }
}
