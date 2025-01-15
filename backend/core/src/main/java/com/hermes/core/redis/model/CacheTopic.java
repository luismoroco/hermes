package com.hermes.core.redis.model;

import lombok.Getter;

import java.time.Duration;

@Getter
public enum CacheTopic {
  SESSION(10);

  private final Duration duration;

  CacheTopic(long duration) {
    this.duration = Duration.ofDays(duration);
  }
}
