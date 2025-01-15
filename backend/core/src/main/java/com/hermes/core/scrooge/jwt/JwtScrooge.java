package com.hermes.core.scrooge.jwt;

import com.hermes.core.common.error.exc.UnauthorizedException;
import com.hermes.core.common.jwt.JwtService;
import com.hermes.core.redis.CacheService;
import com.hermes.core.redis.model.CacheTopic;
import com.hermes.core.scrooge.Scrooge;
import com.hermes.core.common.session.model.SessionUser;
import com.hermes.core.modules.user.model.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Qualifier("jwt")
public class JwtScrooge implements Scrooge<JwtKeys> {
  private final CacheService cacheService;
  private final JwtService jwtService;

  public JwtScrooge(
    CacheService cacheService,
    JwtService jwtService
  ) {
    this.cacheService = cacheService;
    this.jwtService = jwtService;
  }

  private Mono<UserDetails> context() {
    return ReactiveSecurityContextHolder.getContext().map(securityContext
      -> (UserDetails) securityContext.getAuthentication().getPrincipal()
    );
  }

  @Override
  public <U extends User> Mono<JwtKeys> generateKeys(U u) {
    String token = this.jwtService.buildToken(u.getUserName());
    var session = new SessionUser();
    session.setUserId(u.getUserId());
    session.setUserType(u.getUserType());

    return this.cacheService.put(CacheTopic.SESSION, u.getUserName(), session)
      .thenReturn(new JwtKeys(token));
  }

  @Override
  public Mono<Void> destroyKeys() {
    return this.context()
      .flatMap(user -> {
        this.cacheService.evict(CacheTopic.SESSION, user.getUsername());
        return Mono.empty();
      });
  }

  @Override
  public Mono<SessionUser> retrieveKeys() {
    return this.context()
      .flatMap(user ->
        this.cacheService.get(CacheTopic.SESSION, user.getUsername(), SessionUser.class)
          .switchIfEmpty(Mono.error(new UnauthorizedException("Session not found")))
      );
  }
}
