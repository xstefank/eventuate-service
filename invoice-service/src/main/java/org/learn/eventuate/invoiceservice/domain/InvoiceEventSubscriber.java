package org.learn.eventuate.invoiceservice.domain;

import io.eventuate.EventHandlerMethod;
import io.eventuate.EventSubscriber;
import org.learn.eventuate.coreapi.InvoiceInfo;
import org.learn.eventuate.invoiceservice.domain.event.InvoiceProcessedEvent;
import org.learn.eventuate.invoiceservice.domain.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@EventSubscriber(id = "invoiceEventSubscriber")
public class InvoiceEventSubscriber {

    @Autowired
    private InvoiceService invoiceService;

    @EventHandlerMethod
    public void on(InvoiceProcessedEvent event) {
        InvoiceInfo invoiceInfo = new InvoiceInfo(event.getSagaInfo().getSagaId(), event.getInvoice());
        invoiceService.sendInvoice(invoiceInfo);
    }

}
