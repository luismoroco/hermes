package com.hermes.core.modules.order.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {
  private Long productId;
  private Integer quantity;
  private String productName;
  private Double price;
}
