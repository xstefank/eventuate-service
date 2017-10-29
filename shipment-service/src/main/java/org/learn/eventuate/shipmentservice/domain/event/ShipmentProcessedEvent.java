package org.learn.eventuate.shipmentservice.domain.event;

import lombok.NoArgsConstructor;
import org.learn.eventuate.coreapi.OrderSagaInfo;

@NoArgsConstructor
public class ShipmentProcessedEvent implements ShipmentEvent {

    private OrderSagaInfo orderSagaInfo;
    private int price;

    public ShipmentProcessedEvent(OrderSagaInfo orderSagaInfo, int price) {
        this.orderSagaInfo = orderSagaInfo;
        this.price = price;
    }

    public OrderSagaInfo getOrderSagaInfo() {
        return orderSagaInfo;
    }

    public int getPrice() {
        return price;
    }
}
