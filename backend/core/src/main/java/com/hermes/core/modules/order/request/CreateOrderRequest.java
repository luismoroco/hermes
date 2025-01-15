package com.hermes.core.modules.order.request;

import com.hermes.core.modules.currency.CurrencyType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderRequest {
  private Long userId;
  private CurrencyType currencyType;
  private List<OrderItemRequest> items;
}
