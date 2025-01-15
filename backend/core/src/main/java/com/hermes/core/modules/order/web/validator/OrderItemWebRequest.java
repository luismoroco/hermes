package com.hermes.core.modules.order.web.validator;

import com.hermes.core.common.request.RequestAdapter;
import com.hermes.core.modules.order.request.OrderItemRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemWebRequest implements RequestAdapter<OrderItemRequest> {
  @NotNull private Long productId;
  @NotNull private Integer quantity;

  @Override
  public Class<OrderItemRequest> getTargetClass() {
    return OrderItemRequest.class;
  }
}
