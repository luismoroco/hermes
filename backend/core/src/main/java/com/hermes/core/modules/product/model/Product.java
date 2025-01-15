package com.hermes.core.modules.product.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product {
  private Long productId;
  private String name;
  private String description;
  private Boolean enabled;
  private Boolean archived;
  private Boolean featured;
  private Integer stock;
  private Double price;
}
