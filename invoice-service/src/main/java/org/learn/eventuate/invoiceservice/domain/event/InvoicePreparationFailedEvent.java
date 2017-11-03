package org.learn.eventuate.invoiceservice.domain.event;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class InvoicePreparationFailedEvent implements InvoiceEvent {

    private String sagaId;
    private String cause;

    public InvoicePreparationFailedEvent(String sagaId, String cause) {
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
