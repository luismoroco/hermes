package com.hermes.core.modules.user.database;

import com.hermes.core.common.mapping.Mapper;
import com.hermes.core.modules.user.dao.UserDao;
import com.hermes.core.modules.user.database.user.UserEntity;
import com.hermes.core.modules.user.database.user.UserRepository;
import com.hermes.core.modules.user.model.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Qualifier("postgresql")
public class UserDaoPg implements UserDao {
  private final UserRepository repository;

  public UserDaoPg(UserRepository repository) {
    this.repository = repository;
  }

  @Override
  public Mono<User> findByUsername(String username) {
    return this.repository.findByUsername(username).map(UserEntity::toModel);
  }

  @Override
  public Mono<Boolean> existsByUsername(String username) {
    return this.repository.existsByUsername(username);
  }

  @Override
  public Mono<User> saveUser(User user) {
    var userEntity = Mapper.get().map(user, UserEntity.class);
    return this.repository.save(userEntity).map(UserEntity::toModel);
  }
}
