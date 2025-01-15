package com.hermes.core.modules.auth.web.api;

import com.hermes.core.modules.auth.AuthUseCase;
import com.hermes.core.modules.auth.web.validator.LoginWebRequest;
import com.hermes.core.modules.auth.web.validator.SignUpWebRequest;
import com.hermes.core.modules.user.model.User;
import com.hermes.core.scrooge.Keys;
import com.hermes.core.scrooge.Scrooge;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
  private final AuthUseCase useCase;
  private final Scrooge<? extends Keys> scrooge;
  private final ReactiveAuthenticationManager authenticationManager;

  public AuthController(
    AuthUseCase useCase,
    @Qualifier("jwt") Scrooge<? extends Keys> scrooge,
    ReactiveAuthenticationManager authenticationManager
  ) {
    this.useCase = useCase;
    this.scrooge = scrooge;
    this.authenticationManager = authenticationManager;
  }

  @PostMapping("/log-in")
  public Mono<ResponseEntity<? extends Keys>> loginUser(@Valid @RequestBody final LoginWebRequest webRequest) {
    var auth = new UsernamePasswordAuthenticationToken(webRequest.getUsername(), webRequest.getPassword());
    return this.authenticationManager.authenticate(auth)
      .flatMap(authentication -> this.useCase.loginUser(webRequest.buildRequest()))
      .flatMap(this::performAuthorization);
  }

  @PostMapping("/sign-up")
  public Mono<ResponseEntity<? extends Keys>> signUpUser(@Valid @RequestBody final SignUpWebRequest webRequest) {
    return this.useCase.signUpUser(webRequest.buildRequest())
      .flatMap(this::performAuthorization);
  }

  private Mono<ResponseEntity<? extends Keys>> performAuthorization(User user) {
    return this.scrooge.generateKeys(user).map(ResponseEntity::ok);
  }
}
