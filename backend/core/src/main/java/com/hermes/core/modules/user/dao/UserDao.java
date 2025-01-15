package com.hermes.core.modules.user.dao;

import com.hermes.core.modules.user.model.User;
import reactor.core.publisher.Mono;

public interface UserDao {
  Mono<User> findByUsername(String username);
  Mono<Boolean> existsByUsername(String username);
  Mono<User> saveUser(User user);
}
