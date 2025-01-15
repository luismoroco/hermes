package com.hermes.core.redis.model;

import java.time.Duration;

public enum CacheTopic {
  SESSION(10);

  private final Duration duration;

  CacheTopic(long duration) {
    this.duration = Duration.ofMinutes(duration);
  }

  public Duration getDuration() {
    return this.duration;
  }
}
