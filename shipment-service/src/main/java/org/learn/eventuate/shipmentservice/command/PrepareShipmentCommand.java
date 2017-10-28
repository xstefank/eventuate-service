package org.learn.eventuate.shipmentservice.command;

import org.learn.eventuate.coreapi.OrderInfo;

public class PrepareShipmentCommand implements ShipmentCommand {

    private OrderInfo orderInfo;

    public PrepareShipmentCommand(OrderInfo orderInfo) {
        this.orderInfo = orderInfo;
    }

    public OrderInfo getOrderInfo() {
        return orderInfo;
    }
}
