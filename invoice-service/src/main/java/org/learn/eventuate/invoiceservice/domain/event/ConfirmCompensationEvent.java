package org.learn.eventuate.invoiceservice.domain.event;

public class ConfirmCompensationEvent implements InvoiceEvent {

    private String sagaId;

    public ConfirmCompensationEvent(String sagaId) {
        this.sagaId = sagaId;
    }

    public String getSagaId() {
        return sagaId;
    }
}
