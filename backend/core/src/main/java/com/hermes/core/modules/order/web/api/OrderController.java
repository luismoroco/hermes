package com.hermes.core.modules.order.web.api;

import com.hermes.core.common.scrooge.Keys;
import com.hermes.core.common.scrooge.Scrooge;
import com.hermes.core.modules.order.OrderUseCase;
import com.hermes.core.modules.order.model.Order;
import com.hermes.core.modules.order.web.validator.CreateOrderWebRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Map;

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
  public Mono<ResponseEntity<Order>> createOrder(@Valid @RequestBody final CreateOrderWebRequest webRequest) {
    return this.scrooge.retrieveKeys()
      .map(sessionUser -> webRequest.buildRequest(Map.of("userId", sessionUser.getUserId())))
      .flatMap(request -> this.useCase.createOrder(request)
        .map(order -> ResponseEntity.status(HttpStatus.CREATED).body(order))
      );
  }
}
