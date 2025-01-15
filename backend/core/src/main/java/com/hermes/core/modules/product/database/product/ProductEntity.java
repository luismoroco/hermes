package com.hermes.core.modules.product.database.product;

import com.hermes.core.common.mapping.Mapper;
import com.hermes.core.common.model.AuditableModel;
import com.hermes.core.common.model.ModelAdapter;
import com.hermes.core.modules.product.model.Product;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product")
public class ProductEntity extends AuditableModel implements ModelAdapter<Product> {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long productId;
  @NotBlank private String name;
  @NotBlank private String description;
  @NotNull private Boolean enabled = Boolean.TRUE;
  @NotNull private Boolean archived = Boolean.FALSE;
  @NotNull private Boolean featured = Boolean.FALSE;
  @PositiveOrZero private Integer stock;
  @PositiveOrZero private Double price;

  @Override
  public Product toModel() {
    return Mapper.get().map(this, Product.class);
  }
}
