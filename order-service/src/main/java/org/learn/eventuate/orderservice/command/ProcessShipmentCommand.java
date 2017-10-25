package org.learn.eventuate.orderservice.command;

import org.learn.eventuate.coreapi.ShipmentInfo;

public class ProcessShipmentCommand implements OrderSagaCommand {


    private ShipmentInfo shipmentInfo;

    public ProcessShipmentCommand(ShipmentInfo shipmentInfo) {
        this.shipmentInfo = shipmentInfo;
    }

    public ShipmentInfo getShipmentInfo() {
        return shipmentInfo;
    }
}
