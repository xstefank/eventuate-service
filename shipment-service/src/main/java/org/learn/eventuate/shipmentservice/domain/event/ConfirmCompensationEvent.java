package org.learn.eventuate.shipmentservice.domain.event;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ConfirmCompensationEvent implements ShipmentEvent {

    private String sagaId;

    public ConfirmCompensationEvent(String sagaId) {
        this.sagaId = sagaId;
    }

    public String getSagaId() {
        return sagaId;
    }
}
