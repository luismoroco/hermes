package com.hermes.core.modules.user;

import com.hermes.core.common.error.exc.BadRequestException;
import com.hermes.core.modules.user.dao.UserDao;
import com.hermes.core.modules.user.model.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UserService {
  private final UserDao dao;

  public UserService(
    @Qualifier("postgresql") UserDao dao
  ) {
    this.dao = dao;
  }

  public Mono<User> findByUsername(String username) {
    return this.dao.findByUsername(username)
      .switchIfEmpty(Mono.error(new BadRequestException("Username not found")));
  }

  public Mono<Void> existsByUsername(String username) {
    return this.dao.existsByUsername(username)
      .flatMap(userExist -> Boolean.TRUE.equals(userExist)
        ? Mono.error(new BadRequestException("Username already exists"))
        : Mono.empty());
  }

  public Mono<User> saveUser(User user) {
    return this.dao.saveUser(user);
  }
}
