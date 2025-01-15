package com.hermes.core.modules.order.web.validator;

import com.hermes.core.common.RequestAdapter;
import com.hermes.core.modules.order.request.GetOrdersRequest;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetOrdersWebRequest implements RequestAdapter<GetOrdersRequest> {
  @NotNull @Positive private Integer page;
  @NotNull @Positive private Integer size;

  @Override
  public Class<GetOrdersRequest> getTargetClass() {
    return GetOrdersRequest.class;
  }
}
