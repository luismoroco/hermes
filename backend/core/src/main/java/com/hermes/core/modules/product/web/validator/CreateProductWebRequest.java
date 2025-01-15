package com.hermes.core.modules.product.web.validator;

import com.hermes.core.common.RequestAdapter;
import com.hermes.core.modules.product.request.CreateProductRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductWebRequest implements RequestAdapter<CreateProductRequest> {
  @NotBlank private String name;
  @NotBlank private String description;
  @NotNull private Boolean enabled;
  @NotNull private Boolean featured;
  @NotNull @PositiveOrZero private Double price;
  @NotNull @PositiveOrZero private Integer stock;

  @Override
  public Class<CreateProductRequest> getTargetClass() {
    return CreateProductRequest.class;
  }
}
