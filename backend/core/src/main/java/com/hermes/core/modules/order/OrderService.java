package com.hermes.core.modules.order;

import com.hermes.core.common.error.exc.BadRequestException;
import com.hermes.core.modules.order.dao.OrderDao;
import com.hermes.core.modules.order.model.Order;
import com.hermes.core.modules.order.request.OrderItemRequest;
import com.hermes.core.modules.product.ProductService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
  private final OrderDao dao;
  private final ProductService productService;

  public OrderService(
    @Qualifier("mongo") OrderDao dao,
    ProductService productService
  ) {
    this.dao = dao;
    this.productService = productService;
  }

  public Mono<Order> saveOrder(Order order) {
    return this.dao.saveOrder(order);
  }

  public Mono<Void> validateOrderItemsConstraints(List<OrderItemRequest> orderItemRequests) {
    return Flux.fromIterable(orderItemRequests)
      .flatMap(orderItemRequest ->
        this.productService.findProductById(orderItemRequest.getProductId())
          .flatMap(product -> Boolean.TRUE.equals(product.getStock() >= orderItemRequest.getQuantity())
            ? Mono.empty()
            : Mono.error(new BadRequestException("Not enough stock"))
          )
      )
      .then();
  }

  public Flux<Order> getOrders(Optional<Long> userId, Integer page, Integer size) {
    return this.dao.findAllByUserId(userId, page, size);
  }

  public Mono<Order> findByUserIdAndOrderId(Optional<Long> userId, String orderId) {
    return this.dao.findByUserIdAndOrderId(userId, orderId)
      .switchIfEmpty(Mono.error(new BadRequestException("Order not found")));
  }

  public Mono<Order> findByOrderId(String orderId) {
    return this.dao.findById(orderId)
      .switchIfEmpty(Mono.error(new BadRequestException("Order not found")));
  }

  public Mono<Integer> countByOrderIdIn(List<String> orderIds) {
    return this.dao.countByOrderIdIn(orderIds);
  }

  public Mono<Void> deleteByOrderIdIn(List<String> orderIds) {
    return this.dao.deleteByOrderIdIn(orderIds);
  }
}
