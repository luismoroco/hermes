package com.hermes.core.security;

import com.hermes.core.security.repository.JwtSecurityContextRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfig {
  private static final String[] PUBLIC_URLS = { "/api/auth/**" };
  private final JwtAuthenticationManager jwtAuthenticationManager;
  private final JwtSecurityContextRepository jwtSecurityContextRepository;

  public SecurityConfig(
    JwtAuthenticationManager jwtAuthenticationManager,
    JwtSecurityContextRepository jwtSecurityContextRepository
  ) {
    this.jwtAuthenticationManager = jwtAuthenticationManager;
    this.jwtSecurityContextRepository = jwtSecurityContextRepository;
  }

  @Bean
  public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
    return http
      .csrf(ServerHttpSecurity.CsrfSpec::disable)
      .authorizeExchange(exchanges -> exchanges
        .pathMatchers(HttpMethod.POST, PUBLIC_URLS).permitAll()
        .anyExchange().authenticated())
      .authenticationManager(this.jwtAuthenticationManager)
      .securityContextRepository(this.jwtSecurityContextRepository)
      .build();
  }
}
