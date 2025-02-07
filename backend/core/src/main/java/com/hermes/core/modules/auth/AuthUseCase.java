package com.hermes.core.modules.auth;

import com.hermes.core.modules.auth.request.LoginRequest;
import com.hermes.core.modules.auth.request.SignUpRequest;
import com.hermes.core.modules.user.UserService;
import com.hermes.core.modules.user.model.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
public class AuthUseCase {
  private final UserService userService;
  private final PasswordEncoder passwordEncoder;

  public AuthUseCase(
    UserService userService,
    @Qualifier("BCrypt") PasswordEncoder passwordEncoder
  ) {
    this.userService = userService;
    this.passwordEncoder = passwordEncoder;
  }

  public Mono<User> loginUser(final LoginRequest request) {
    return this.userService.findByUsername(request.getUsername());
  }

  @Transactional
  public Mono<User> signUpUser(final SignUpRequest request) {
    return this.userService.existsByUsername(request.getUsername())
      .then(Mono.defer(() -> {
        var user = new User();
        user.setUserType(request.getUserType());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        return this.userService.saveUser(user);
      }));
  }
}
