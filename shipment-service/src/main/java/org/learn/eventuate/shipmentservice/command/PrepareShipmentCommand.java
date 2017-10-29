package org.learn.eventuate.shipmentservice.command;

import org.learn.eventuate.coreapi.OrderSagaInfo;

public class PrepareShipmentCommand implements ShipmentCommand {

    private OrderSagaInfo orderSagaInfo;

    public PrepareShipmentCommand(OrderSagaInfo orderSagaInfo) {
        this.orderSagaInfo = orderSagaInfo;
    }

    public OrderSagaInfo getOrderSagaInfo() {
        return orderSagaInfo;
    }
}
