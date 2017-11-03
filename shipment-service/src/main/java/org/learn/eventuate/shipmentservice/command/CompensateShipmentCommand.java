package org.learn.eventuate.shipmentservice.command;

import lombok.NoArgsConstructor;
import org.learn.eventuate.coreapi.ParticipantFailureInfo;

@NoArgsConstructor
public class CompensateShipmentCommand implements ShipmentCommand {

    private ParticipantFailureInfo failureInfo;

    public CompensateShipmentCommand(ParticipantFailureInfo failureInfo) {
        this.failureInfo = failureInfo;
    }

    public ParticipantFailureInfo getFailureInfo() {
        return failureInfo;
    }
}
