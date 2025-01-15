package com.hermes.core.redis;

import com.hermes.core.redis.model.CacheTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CacheService {
  private static final Logger LOGGER = LoggerFactory.getLogger(CacheService.class);
  private final ReactiveRedisTemplate<String, Object> redisTemplate;

  public CacheService(ReactiveRedisTemplate<String, Object> redisTemplate) {
    this.redisTemplate = redisTemplate;
  }

  private static String makeKey(CacheTopic topic, String key) {
    return String.format("%s:%s", topic.name(), key);
  }

  public <V> Mono<Void> put(CacheTopic topic, String key, V value) {
    return redisTemplate.opsForValue()
      .set(makeKey(topic, key), value, topic.getDuration())
      .doOnSuccess(unused ->
        LOGGER.info("Value added [topic={}][key={}][value={}]", topic, key, value))
      .then();
  }

  public <V> Mono<V> get(CacheTopic topic, String key, Class<V> valueType) {
    return redisTemplate.opsForValue()
      .get(makeKey(topic, key))
      .cast(valueType)
      .doOnNext(value ->
        LOGGER.info("Cache hit [topic={}][key={}][value={}]", topic, key, value))
      .switchIfEmpty(Mono.defer(() -> {
        LOGGER.warn("Cache miss [topic={}][key={}]", topic, key);
        return Mono.empty();
      }));
  }

  public Mono<Void> evict(CacheTopic topic, String key) {
    return redisTemplate.opsForValue()
      .delete(makeKey(topic, key))
      .doOnNext(deleted -> {
        if (deleted) {
          LOGGER.info("Value removed [topic={}][key={}]", topic, key);
        } else {
          LOGGER.warn("Failed to remove value [topic={}][key={}]", topic, key);
        }
      })
      .then();
  }
}
