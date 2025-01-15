package com.hermes.core.modules.product.web.validator;

import com.hermes.core.common.request.RequestAdapter;
import com.hermes.core.modules.product.request.CreateProductRequest;

public class CreateProductWebRequest implements RequestAdapter<CreateProductRequest> {
  @Override
  public Class<CreateProductRequest> getTargetClass() {
    return CreateProductRequest.class;
  }
}
