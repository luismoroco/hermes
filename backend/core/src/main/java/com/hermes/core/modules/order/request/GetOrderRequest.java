package com.hermes.core.modules.order.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetOrderRequest {
  private String orderId;
  private Optional<Long> userId;
}
