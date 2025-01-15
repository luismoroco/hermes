package com.hermes.core.modules.order.database.order;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends ReactiveMongoRepository<OrderEntity, String> {
  Flux<OrderEntity> findAllByUserIdIn(List<Long> userIds);
  Flux<OrderEntity> findAllByUserId(Optional<Long> userId, Pageable pageable);

  @Query("{ $or: [ { 'userId': ?0 }, { ?0: { $exists: false } } ] }")
  Mono<OrderEntity> findByUserIdAndOrderId(Optional<Long> userId, String orderId);
}
