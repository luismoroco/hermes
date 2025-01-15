package com.hermes.core.modules.order.web.validator;

import com.hermes.core.common.RequestAdapter;
import com.hermes.core.modules.currency.CurrencyType;
import com.hermes.core.modules.order.request.CreateOrderRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderWebRequest implements RequestAdapter<CreateOrderRequest> {
  @NotNull CurrencyType currencyType;
  @NotNull List<OrderItemWebRequest> items;

  @Override
  public Class<CreateOrderRequest> getTargetClass() {
    return CreateOrderRequest.class;
  }
}
