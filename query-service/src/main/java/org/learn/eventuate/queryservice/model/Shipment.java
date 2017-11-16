package org.learn.eventuate.queryservice.model;

import org.springframework.data.annotation.Id;

public class Shipment {

    @Id
    private String id;

    private String shipmentId;
    private int price;
    private boolean canceled;

    public Shipment(String shipmentId, int price) {
        this.shipmentId = shipmentId;
        this.price = price;
    }

    public String getShipmentId() {
        return shipmentId;
    }

    public int getPrice() {
        return price;
    }

    public boolean isCanceled() {
        return canceled;
    }

    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }

    @Override
    public String toString() {
        return String.format("id: %s, price: %d", shipmentId, price);
    }
}
