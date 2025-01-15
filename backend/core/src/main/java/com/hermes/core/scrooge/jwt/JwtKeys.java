package com.hermes.core.scrooge.jwt;

import com.hermes.core.scrooge.Keys;
import lombok.Getter;

@Getter
public class JwtKeys extends Keys {
  private final String token;

  public JwtKeys(String token) {
    this.setProvider("JWT");
    this.token = token;
  }
}
