package com.hermes.core.modules.order.database.order;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.List;

@Repository
public interface OrderRepository extends ReactiveMongoRepository<OrderEntity, String> {
  Flux<OrderEntity> findAllByUserIdIn(List<Long> userIds);
}
