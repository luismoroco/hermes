package com.hermes.core.modules.order.dto;

import com.hermes.core.modules.currency.CurrencyType;
import com.hermes.core.modules.order.model.OrderStatusType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MinimalOrderDTO {
  private String orderId;
  private OrderStatusType orderStatusType;
  private CurrencyType currencyType;
  private Double amount;
}
