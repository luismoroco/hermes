package com.hermes.core.modules.order.database.order_item;

import com.hermes.core.common.mapping.Mapper;
import com.hermes.core.common.model.ModelAdapter;
import com.hermes.core.modules.order.model.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemEntity implements ModelAdapter<OrderItem> {
  private Long productId;
  private Integer quantity;

  @Override
  public OrderItem toModel() {
    return Mapper.get().map(this, OrderItem.class);
  }
}
