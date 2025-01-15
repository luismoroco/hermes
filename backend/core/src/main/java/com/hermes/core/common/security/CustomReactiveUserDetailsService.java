package com.hermes.core.common.security;

import com.hermes.core.modules.user.database.user.UserRepository;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CustomReactiveUserDetailsService implements ReactiveUserDetailsService {
  private final UserRepository userRepository;

  public CustomReactiveUserDetailsService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public Mono<UserDetails> findByUsername(String username) {
    return this.userRepository.findByUsername(username)
      .switchIfEmpty(Mono.error(new UsernameNotFoundException("Username not found")))
      .cast(UserDetails.class);
  }
}
