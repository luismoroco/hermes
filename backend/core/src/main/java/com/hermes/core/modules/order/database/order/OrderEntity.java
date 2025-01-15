package com.hermes.core.modules.order.database.order;

import com.hermes.core.common.mapping.Mapper;
import com.hermes.core.common.model.ModelAdapter;
import com.hermes.core.modules.currency.CurrencyType;
import com.hermes.core.modules.order.database.order_item.OrderItemEntity;
import com.hermes.core.modules.order.model.Order;
import com.hermes.core.modules.order.model.OrderStatusType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "order")
public class OrderEntity implements ModelAdapter<Order> {
  @Id private String orderId;
  private Long userId;
  private OrderStatusType orderStatusType;
  private CurrencyType currencyType;
  private Double amount;

  private List<OrderItemEntity> orderItems;

  @Override
  public Order toModel() {
    return Mapper.get().map(this, Order.class);
  }
}
