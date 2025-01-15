package com.hermes.core.redis.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {
  @Value("${spring.data.redis.host}")
  private String host;

  @Value("${spring.data.redis.port}")
  private Integer port;

  @Bean
  public ReactiveRedisConnectionFactory redisConnectionFactory() {
    return new LettuceConnectionFactory(this.host, this.port);
  }

  @Bean
  public ReactiveRedisTemplate<String, Object> reactiveRedisTemplate(ReactiveRedisConnectionFactory connectionFactory) {
    RedisSerializationContext.RedisSerializationContextBuilder<String, Object> builder =
      RedisSerializationContext.newSerializationContext(new StringRedisSerializer());

    RedisSerializationContext<String, Object> context = builder
      .value(new GenericJackson2JsonRedisSerializer())
      .build();

    return new ReactiveRedisTemplate<>(connectionFactory, context);
  }
}
