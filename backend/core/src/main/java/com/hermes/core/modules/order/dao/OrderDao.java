package com.hermes.core.modules.order.dao;

import com.hermes.core.modules.order.model.Order;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface OrderDao {
  Mono<Order> findById(String orderId);
  Flux<Order> findAllByUserId(Long userId);
  Mono<Order> saveOrder(Order order);
}
