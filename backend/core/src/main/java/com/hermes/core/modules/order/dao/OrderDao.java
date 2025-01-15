package com.hermes.core.modules.order.dao;

import com.hermes.core.modules.order.model.Order;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

public interface OrderDao {
  Mono<Order> findById(String orderId);
  Flux<Order> findAllByUserId(Long userId);
  Mono<Order> saveOrder(Order order);
  Flux<Order> findAllByUserId(Optional<Long> userId, Integer page, Integer size);
  Mono<Order> findByUserIdAndOrderId(Optional<Long> userId, String orderId);
}
