package com.hermes.core.modules.order.database;

import com.hermes.core.common.mapping.Mapper;
import com.hermes.core.modules.order.dao.OrderDao;
import com.hermes.core.modules.order.database.order.OrderEntity;
import com.hermes.core.modules.order.database.order.OrderRepository;
import com.hermes.core.modules.order.model.Order;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@Service
@Qualifier("mongo")
public class OrderDaoMg implements OrderDao {
  private final OrderRepository repository;
  private final ReactiveMongoTemplate template;

  public OrderDaoMg(
    OrderRepository repository,
    ReactiveMongoTemplate template
  ) {
    this.repository = repository;
    this.template = template;
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
    var cb = new Criteria();
    if (userId.isPresent()) {
      cb = Criteria.where("userId").is(userId.get());
    }

    var query = Query.query(cb).with(PageRequest.of(page - 1, size));
    return Flux.from(template.find(query, OrderEntity.class)).map(OrderEntity::toModel);
  }

  @Override
  public Mono<Order> findByUserIdAndOrderId(Optional<Long> userId, String orderId) {
    var criteria = Criteria.where("orderId").is(orderId);
    userId.ifPresent(id -> criteria.and("userId").is(id));

    var query = Query.query(criteria);
    return Mono.defer(() -> this.template.findOne(query, OrderEntity.class)
      .map(OrderEntity::toModel));
  }

  @Override
  public Mono<Integer> countByOrderIdIn(List<String> orderIds) {
    return this.repository.countAllByOrderIdIn(orderIds).map(Long::intValue);
  }

  @Override
  public Mono<Void> deleteByOrderIdIn(List<String> orderIds) {
    return this.repository.deleteAllByOrderIdIn(orderIds);
  }
}
