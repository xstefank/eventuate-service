package org.learn.eventuate.invoiceservice.domain.event;

import lombok.NoArgsConstructor;
import org.learn.eventuate.coreapi.OrderSagaInfo;

@NoArgsConstructor
public class InvoiceProcessedEvent implements InvoiceEvent {

    private OrderSagaInfo sagaInfo;
    private String invoice;

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
