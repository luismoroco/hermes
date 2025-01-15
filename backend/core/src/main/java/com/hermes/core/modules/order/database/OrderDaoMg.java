package com.hermes.core.modules.order.database;

import com.hermes.core.common.mapping.Mapper;
import com.hermes.core.modules.order.dao.OrderDao;
import com.hermes.core.modules.order.database.order.OrderEntity;
import com.hermes.core.modules.order.database.order.OrderRepository;
import com.hermes.core.modules.order.model.Order;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@Service
@Qualifier("mongo")
public class OrderDaoMg implements OrderDao {
  private final OrderRepository repository;

  public OrderDaoMg(
    OrderRepository repository
  ) {
    this.repository = repository;
  }

  @Override
  public Mono<Order> findById(String orderId) {
    return this.repository.findById(orderId).map(OrderEntity::toModel);
  }

  @Override
  public Flux<Order> findAllByUserId(Long userId) {
    return this.repository.findAllByUserIdIn(List.of(userId)).map(OrderEntity::toModel);
  }

  @Override
  public Mono<Order> saveOrder(Order order) {
    var orderEntity = Mapper.get().map(order, OrderEntity.class);
    return this.repository.save(orderEntity).map(OrderEntity::toModel);
  }

  @Override
  public Flux<Order> findAllByUserId(Optional<Long> userId, Integer page, Integer size) {
    return this.repository.findAllByUserId(userId, PageRequest.of(page - 1, size))
      .map(OrderEntity::toModel);
  }

  @Override
  public Mono<Order> findByUserIdAndOrderId(Optional<Long> userId, String orderId) {
    return this.repository.findByUserIdAndOrderId(userId, orderId).map(OrderEntity::toModel);
  }
}
