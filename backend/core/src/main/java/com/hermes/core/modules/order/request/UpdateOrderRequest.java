package com.hermes.core.modules.order.request;

import com.hermes.core.modules.order.model.OrderStatusType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateOrderRequest {
  private String orderId;
  private OrderStatusType orderStatusType;
}
