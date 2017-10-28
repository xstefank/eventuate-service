package org.learn.eventuate.orderservice.domain.event;

import io.eventuate.Event;

public class OrderSagaComletedEvent implements Event {
    private String orderId;

    public OrderSagaComletedEvent(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }
}
