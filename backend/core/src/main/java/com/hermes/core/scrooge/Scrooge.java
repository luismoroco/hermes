package com.hermes.core.scrooge;

import com.hermes.core.common.session.model.SessionUser;
import com.hermes.core.modules.user.model.User;
import reactor.core.publisher.Mono;

public interface Scrooge<K extends Keys> {
  <U extends User> Mono<K> generateKeys(U u);
  Mono<Void> destroyKeys();
  Mono<SessionUser> retrieveKeys();
}
