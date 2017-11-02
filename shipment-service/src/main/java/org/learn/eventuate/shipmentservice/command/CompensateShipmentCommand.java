package org.learn.eventuate.shipmentservice.command;

import lombok.NoArgsConstructor;
import org.learn.eventuate.coreapi.ShipmentFailureInfo;

@NoArgsConstructor
public class CompensateShipmentCommand implements ShipmentCommand {

    private ShipmentFailureInfo failureInfo;

    public CompensateShipmentCommand(ShipmentFailureInfo failureInfo) {
        this.failureInfo = failureInfo;
    }

    public ShipmentFailureInfo getFailureInfo() {
        return failureInfo;
    }
}
