package com.hermes.core.security.repository;

import com.hermes.core.common.jwt.JwtService;
import com.hermes.core.security.JwtAuthenticationManager;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class JwtSecurityContextRepository implements ServerSecurityContextRepository {
  private final JwtAuthenticationManager authenticationManager;

  public JwtSecurityContextRepository(
    JwtAuthenticationManager authenticationManager
  ) {
    this.authenticationManager = authenticationManager;
  }

  @Override
  public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
    throw new UnsupportedOperationException("Not supported yet");
  }

  @Override
  public Mono<SecurityContext> load(ServerWebExchange exchange) {
    ServerHttpRequest request = exchange.getRequest();
    return Mono.justOrEmpty(JwtService.getBearerToken(request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION)))
      .flatMap(token -> {
        var authentication = new UsernamePasswordAuthenticationToken(token, token);
        return this.authenticationManager.authenticate(authentication)
          .map(SecurityContextImpl::new);
      });
  }
}
