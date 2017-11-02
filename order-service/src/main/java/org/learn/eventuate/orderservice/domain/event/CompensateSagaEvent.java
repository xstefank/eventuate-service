package org.learn.eventuate.orderservice.domain.event;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CompensateSagaEvent implements OrderSagaEvent {

    private String cause;
    private String shipmentId;
    private String invoiceId;

    public CompensateSagaEvent(String cause, String shipmentId, String invoiceId) {
        this.cause = cause;
        this.shipmentId = shipmentId;
        this.invoiceId = invoiceId;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public String getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(String shipmentId) {
        this.shipmentId = shipmentId;
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }
}
