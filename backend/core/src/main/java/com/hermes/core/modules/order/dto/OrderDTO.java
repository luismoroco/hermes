package com.hermes.core.modules.order.dto;

import com.hermes.core.modules.currency.CurrencyType;
import com.hermes.core.modules.order.model.OrderItem;
import com.hermes.core.modules.order.model.OrderStatusType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
  private String orderId;
  private Long userId;
  private OrderStatusType orderStatusType;
  private CurrencyType currencyType;
  private Double amount;

  private List<OrderItem> orderItems;
}
