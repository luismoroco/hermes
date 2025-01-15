package com.hermes.core.modules.product.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductRequest {
  private String name;
  private String description;
  private Double price;
  private Boolean enabled;
  private Boolean featured;
  private Integer stock;
}
