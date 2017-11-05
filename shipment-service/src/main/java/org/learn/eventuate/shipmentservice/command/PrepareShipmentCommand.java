package org.learn.eventuate.shipmentservice.command;

import lombok.NoArgsConstructor;
import org.learn.eventuate.coreapi.OrderSagaInfo;

@NoArgsConstructor
public class PrepareShipmentCommand implements ShipmentCommand {

    private OrderSagaInfo orderSagaInfo;

    public PrepareShipmentCommand(OrderSagaInfo orderSagaInfo) {
        this.orderSagaInfo = orderSagaInfo;
    }

    public OrderSagaInfo getOrderSagaInfo() {
        return orderSagaInfo;
    }
}
