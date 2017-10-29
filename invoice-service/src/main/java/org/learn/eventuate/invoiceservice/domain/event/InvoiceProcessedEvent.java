package org.learn.eventuate.invoiceservice.domain.event;

import org.learn.eventuate.coreapi.OrderSagaInfo;

public class InvoiceProcessedEvent implements InvoiceEvent {
    private final OrderSagaInfo sagaInfo;
    private final String invoice;

    public InvoiceProcessedEvent(OrderSagaInfo sagaInfo, String invoice) {
        this.sagaInfo = sagaInfo;
        this.invoice = invoice;
    }

    public OrderSagaInfo getSagaInfo() {
        return sagaInfo;
    }

    public String getInvoice() {
        return invoice;
    }
}
