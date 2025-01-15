package com.hermes.core.modules.order.model;

import com.hermes.core.modules.currency.CurrencyType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Order {
  private String orderId;
  private Long userId;
  private OrderStatusType orderStatusType;
  private CurrencyType currencyType;
  private Double amount;

  private List<OrderItem> orderItems;
}
