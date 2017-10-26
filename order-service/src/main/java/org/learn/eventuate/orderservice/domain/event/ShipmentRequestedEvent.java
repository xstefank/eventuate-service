package org.learn.eventuate.orderservice.domain.event;

import lombok.NoArgsConstructor;
import org.learn.eventuate.orderservice.domain.model.OrderInfo;

@NoArgsConstructor
public class ShipmentRequestedEvent implements OrderSagaEvent {

    private OrderInfo orderInfo;

    public ShipmentRequestedEvent(OrderInfo orderInfo) {
        this.orderInfo = orderInfo;
    }

    public OrderInfo getOrderInfo() {
        return orderInfo;
    }
}
