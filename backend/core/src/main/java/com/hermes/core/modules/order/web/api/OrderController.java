package com.hermes.core.modules.order.web.api;

import com.hermes.core.common.scrooge.Keys;
import com.hermes.core.common.scrooge.Scrooge;
import com.hermes.core.modules.order.OrderUseCase;
import com.hermes.core.modules.order.dto.MinimalOrderDTO;
import com.hermes.core.modules.order.dto.OrderDTO;
import com.hermes.core.modules.order.request.DeleteOrderRequest;
import com.hermes.core.modules.order.request.GetOrderRequest;
import com.hermes.core.modules.order.web.validator.CreateOrderWebRequest;
import com.hermes.core.modules.order.web.validator.GetOrdersWebRequest;
import com.hermes.core.modules.user.model.UserType;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
  private final OrderUseCase useCase;
  private final Scrooge<? extends Keys> scrooge;

  public OrderController(
    OrderUseCase useCase,
    @Qualifier("jwt") Scrooge<? extends Keys> scrooge
  ) {
    this.useCase = useCase;
    this.scrooge = scrooge;
  }

  @PostMapping("")
  @PreAuthorize("hasRole('CLIENT')")
  public Mono<ResponseEntity<OrderDTO>> createOrder(@Valid @RequestBody final CreateOrderWebRequest webRequest) {
    return this.scrooge.retrieveKeys()
      .map(sessionUser -> webRequest.buildRequest(Map.of("userId", sessionUser.getUserId())))
      .flatMap(request -> this.useCase.createOrder(request)
        .map(order -> ResponseEntity.status(HttpStatus.CREATED).body(order))
      );
  }

  @GetMapping("")
  @PreAuthorize("hasAnyRole('CLIENT', 'MANAGER')")
  public Mono<ResponseEntity<Flux<MinimalOrderDTO>>> getOrders(@Valid final GetOrdersWebRequest webRequest) {
    return this.scrooge.retrieveKeys()
      .map(sessionUser -> {
        var request = webRequest.buildRequest(
          Map.of(
          "userId", sessionUser.getUserType().equals(UserType.CLIENT)
            ? Optional.of(sessionUser.getUserId())
            : Optional.empty()
        ));
        return this.useCase.getOrders(request);
      })
      .map(orders -> ResponseEntity.ok().body(orders));
  }

  @GetMapping("/{orderId}")
  @PreAuthorize("hasAnyRole('CLIENT', 'MANAGER')")
  public Mono<ResponseEntity<OrderDTO>> getOrder(@PathVariable final String orderId) {
    return this.scrooge.retrieveKeys()
      .flatMap(sessionUser -> {
        var request = new GetOrderRequest();
        request.setOrderId(orderId);
        request.setUserId(sessionUser.getUserType().equals(UserType.CLIENT)
          ? Optional.of(sessionUser.getUserId())
          : Optional.empty());

        return this.useCase.getOrder(request)
          .map(order -> ResponseEntity.ok().body(order));
      });
  }

  @DeleteMapping("/{orderId}")
  @PreAuthorize("hasRole('MANAGER')")
  public Mono<ResponseEntity<Void>> deleteOrder(@PathVariable final String orderId) {
    return this.useCase.deleteOrder(new DeleteOrderRequest(orderId))
      .then(Mono.just(ResponseEntity.noContent().build()));
  }
}
