package com.hermes.core.security;

import com.hermes.core.common.error.exc.BadRequestException;
import com.hermes.core.common.jwt.JwtService;
import com.hermes.core.common.session.model.SessionUser;
import com.hermes.core.redis.CacheService;
import com.hermes.core.redis.model.CacheTopic;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Component
public class JwtAuthenticationManager implements ReactiveAuthenticationManager {
  private final JwtService jwtService;
  private final ReactiveUserDetailsService userDetailsService;
  private final CacheService cacheService;

  public JwtAuthenticationManager(
    JwtService jwtService,
    ReactiveUserDetailsService userDetailsService,
    CacheService cacheService
  ) {
    this.jwtService = jwtService;
    this.userDetailsService = userDetailsService;
    this.cacheService = cacheService;
  }

  @Override
  public Mono<Authentication> authenticate(Authentication authentication) {
    String token = authentication.getCredentials().toString();

    System.out.println("token: " + token);

    String username = this.jwtService.getSubject(token);
    if (Objects.nonNull(username) && this.jwtService.isValid(token, username)) {
      return this.cacheService.get(CacheTopic.SESSION, username, SessionUser.class)
        .switchIfEmpty(Mono.error(new BadRequestException("Token invalid")))
        .then(this.userDetailsService.findByUsername(username)
          .map(user ->
            new UsernamePasswordAuthenticationToken(
              user,
              null,
              user.getAuthorities())
          ));
    }

    return Mono.empty();
  }
}
