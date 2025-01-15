package com.hermes.core.modules.order.database.order;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends ReactiveMongoRepository<OrderEntity, String> {
  Flux<OrderEntity> findAllByUserIdIn(List<Long> userIds);
  Flux<OrderEntity> findByUserId(Optional<Long> userId, Pageable pageable);
}
