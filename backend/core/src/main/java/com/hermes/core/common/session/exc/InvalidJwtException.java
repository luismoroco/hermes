package com.hermes.core.common.session.exc;

import com.hermes.core.common.error.HermesException;

public class InvalidJwtException extends HermesException {
  public InvalidJwtException(String message) {
    super(message);
  }
}
