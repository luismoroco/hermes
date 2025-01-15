package com.hermes.core.modules.order.database.order;

import com.hermes.core.common.mapping.Mapper;
import com.hermes.core.common.model.ModelAdapter;
import com.hermes.core.modules.currency.CurrencyType;
import com.hermes.core.modules.order.database.order_item.OrderItemEntity;
import com.hermes.core.modules.order.model.Order;
import com.hermes.core.modules.order.model.OrderStatusType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "order")
public class OrderEntity implements ModelAdapter<Order> {
  @Id private String orderId;
  @NotNull private Long userId;
  @Enumerated(EnumType.STRING) private OrderStatusType orderStatusType;
  @Enumerated(EnumType.STRING) private CurrencyType currencyType;
  @PositiveOrZero private Double amount;

  @Transient private List<OrderItemEntity> orderItems;

  @Override
  public Order toModel() {
    return Mapper.get().map(this, Order.class);
  }
}
