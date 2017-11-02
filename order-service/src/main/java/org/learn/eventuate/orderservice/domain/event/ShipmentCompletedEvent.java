package org.learn.eventuate.orderservice.domain.event;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ShipmentCompletedEvent implements OrderSagaEvent {

    private String shipmentId;

    public ShipmentCompletedEvent(String shipmentId) {
        this.shipmentId = shipmentId;
    }

    public String getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(String shipmentId) {
        this.shipmentId = shipmentId;
    }
}
