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
}
