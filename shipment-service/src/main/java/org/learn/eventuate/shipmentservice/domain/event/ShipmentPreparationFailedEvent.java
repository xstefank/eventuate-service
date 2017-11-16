package org.learn.eventuate.shipmentservice.domain.event;

import org.learn.eventuate.coreapi.ShipmentEvent;

public class ShipmentPreparationFailedEvent implements ShipmentEvent {

    private final String sagaId;
    private final String cause;

    public ShipmentPreparationFailedEvent(String sagaId, String cause) {
        this.sagaId = sagaId;
        this.cause = cause;
    }

    public String getSagaId() {
        return sagaId;
    }

    public String getCause() {
        return cause;
    }
}
