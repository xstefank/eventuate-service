package org.learn.eventuate.orderservice.domain.event;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class OrderSagaComletedEvent implements OrderSagaEvent {
    private String orderId;

    public OrderSagaComletedEvent(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }
}
