package org.learn.eventuate.shipmentservice.domain.event;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ComfirmCompensationEvent implements ShipmentEvent {

    private String sagaId;

    public ComfirmCompensationEvent(String sagaId) {
        this.sagaId = sagaId;
    }

    public String getSagaId() {
        return sagaId;
    }
}
