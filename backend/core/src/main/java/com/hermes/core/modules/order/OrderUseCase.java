package com.hermes.core.modules.order;

import com.hermes.core.modules.order.model.Order;
import com.hermes.core.modules.order.model.OrderItem;
import com.hermes.core.modules.order.model.OrderStatusType;
import com.hermes.core.modules.order.request.CreateOrderRequest;
import com.hermes.core.modules.order.request.OrderItemRequest;
import com.hermes.core.modules.product.ProductService;
import com.hermes.core.modules.product.model.Product;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderUseCase {
  private final OrderService service;
  private final ProductService productService;

  public OrderUseCase(
    OrderService service,
    ProductService productService
  ) {
    this.service = service;
    this.productService = productService;
  }

  public Mono<Order> createOrder(final CreateOrderRequest request) {
    return this.service.validateOrderItemsConstraints(request.getItems())
      .then(Mono.defer(() -> {
        var order = new Order();
        order.setUserId(request.getUserId());
        order.setOrderStatusType(OrderStatusType.PENDING);
        order.setCurrencyType(request.getCurrencyType());

        List<Long> productIds = request.getItems().stream()
          .map(OrderItemRequest::getProductId)
          .toList();

        return this.productService.findAllByProductIdIn(productIds)
          .collectMap(Product::getProductId)
          .flatMap(productsMap -> {
            var items = new ArrayList<OrderItem>();

            double totalAmount = 0;
            for (var orderItemRequest : request.getItems()) {
              var product = productsMap.get(orderItemRequest.getProductId());
              totalAmount += product.getPrice() * orderItemRequest.getQuantity();

              var orderItem = new OrderItem();
              orderItem.setProductId(orderItemRequest.getProductId());
              orderItem.setQuantity(orderItemRequest.getQuantity());
              orderItem.setProductName(product.getName());
              orderItem.setPrice(product.getPrice());

              items.add(orderItem);
            }

            order.setAmount(totalAmount);
            order.setOrderItems(items);

            return this.service.saveOrder(order);
          });
      }));
  }
}
