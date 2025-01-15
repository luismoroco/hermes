package com.hermes.core.modules.product.database.product;

import com.hermes.core.common.mapping.Mapper;
import com.hermes.core.common.ModelAdapter;
import com.hermes.core.modules.product.model.Product;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.sql.Timestamp;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product")
public class ProductEntity implements ModelAdapter<Product> {
  @Id private Long productId;
  private String name;
  private String description;
  private Boolean enabled = Boolean.TRUE;
  private Boolean archived = Boolean.FALSE;
  private Boolean featured = Boolean.FALSE;
  private Integer stock;
  private Double price;

  private Timestamp createdAt;
  private Timestamp updatedAt;

  @Override
  public Product toModel() {
    return Mapper.get().map(this, Product.class);
  }
}
