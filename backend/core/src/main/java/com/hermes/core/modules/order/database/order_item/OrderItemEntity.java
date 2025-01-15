package com.hermes.core.modules.order.database.order_item;

import com.hermes.core.common.mapping.Mapper;
import com.hermes.core.common.model.ModelAdapter;
import com.hermes.core.modules.order.model.OrderItem;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "order_item")
public class OrderItemEntity implements ModelAdapter<OrderItem> {
  @Id private String orderItemId;
  private String orderId;
  private Long productId;
  private Integer quantity;

  @Override
  public OrderItem toModel() {
    return Mapper.get().map(this, OrderItem.class);
  }
}
