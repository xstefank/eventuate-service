package org.learn.eventuate.orderservice.domain.event;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class InvoiceCompletedEvent implements OrderSagaEvent {

    private String invoiceId;

    public InvoiceCompletedEvent(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getInvoiceId() {
        return invoiceId;
    }
}
