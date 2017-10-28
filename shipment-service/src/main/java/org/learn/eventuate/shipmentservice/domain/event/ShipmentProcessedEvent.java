package org.learn.eventuate.shipmentservice.domain.event;

import lombok.NoArgsConstructor;
import org.learn.eventuate.coreapi.OrderInfo;

@NoArgsConstructor
public class ShipmentProcessedEvent implements ShipmentEvent {

    private OrderInfo orderInfo;
    private int price;

    public ShipmentProcessedEvent(OrderInfo orderInfo, int price) {
        this.orderInfo = orderInfo;
        this.price = price;
    }

    public OrderInfo getOrderInfo() {
        return orderInfo;
    }

    public int getPrice() {
        return price;
    }
}
