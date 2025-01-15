package com.hermes.core.common.jwt;

import com.hermes.core.common.session.exc.InvalidJwtException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;
import java.util.function.Function;

@Service
public class JwtService {
  private static final String BEARER_AUTH_PREFIX = "Bearer ";
  @Value("${application.security.jwt.secret-key}")
  private String secretKey;
  @Value("${application.security.jwt.expiration}")
  private Long jwtExpiration;

  public static Optional<String> getBearerToken(String header) {
    if (StringUtils.hasText(header) && header.startsWith(BEARER_AUTH_PREFIX)) {
      return Optional.of(header.substring(BEARER_AUTH_PREFIX.length()));
    }

    return Optional.empty();
  }

  private Key getSignInKey() {
    return Keys.hmacShaKeyFor(Decoders.BASE64.decode(this.secretKey));
  }

  public String buildToken(String subject) {
    return Jwts.builder()
      .setClaims(new HashMap<>())
      .setSubject(subject)
      .setIssuedAt(new Date(System.currentTimeMillis()))
      .setExpiration(new Date(System.currentTimeMillis() + this.jwtExpiration))
      .signWith(this.getSignInKey(), SignatureAlgorithm.HS256)
      .compact();
  }

  public boolean isValid(String token, String subject) {
    return this.getSubject(token).equals(subject) && !this.getClaimGateway(token, Claims::getExpiration).before(new Date());
  }

  public String getSubject(String token) {
    return this.getClaimGateway(token, Claims::getSubject);
  }

  private <T> T getClaimGateway(
    String token,
    Function<Claims, T> resolver
  ) throws InvalidJwtException {
    try {
      var claims = Jwts
        .parserBuilder()
        .setSigningKey(this.getSignInKey())
        .build()
        .parseClaimsJws(token)
        .getBody();

      return resolver.apply(claims);
    } catch (
      MalformedJwtException |
      SignatureException |
      IllegalArgumentException e
    ) {
      throw new InvalidJwtException("Invalid jwt token");
    }
  }
}
