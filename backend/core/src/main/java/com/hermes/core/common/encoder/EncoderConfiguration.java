package com.hermes.core.common.encoder;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class EncoderConfiguration {
  @Bean
  @Qualifier("BCrypt")
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
