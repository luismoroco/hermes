package com.hermes.core.modules.order.web.validator;

import com.hermes.core.common.RequestAdapter;
import com.hermes.core.modules.order.model.OrderStatusType;
import com.hermes.core.modules.order.request.UpdateOrderRequest;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateOrderWebRequest implements RequestAdapter<UpdateOrderRequest> {
  @Nullable private OrderStatusType orderStatusType;

  @Override
  public Class<UpdateOrderRequest> getTargetClass() {
    return UpdateOrderRequest.class;
  }
}
