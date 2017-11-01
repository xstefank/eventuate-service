package org.learn.eventuate.invoiceservice.domain;

import io.eventuate.DispatchedEvent;
import io.eventuate.EventHandlerMethod;
import io.eventuate.EventSubscriber;
import org.learn.eventuate.coreapi.InvoiceInfo;
import org.learn.eventuate.invoiceservice.domain.event.InvoiceProcessedEvent;
import org.learn.eventuate.invoiceservice.domain.service.InvoiceService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@EventSubscriber(id = "invoiceEventSubscriber")
public class InvoiceEventSubscriber {

    @Autowired
    private InvoiceService invoiceService;

    @EventHandlerMethod
    public void on(DispatchedEvent<InvoiceProcessedEvent> dispatchedEvent) {
        LoggerFactory.getLogger(InvoiceEventSubscriber.class).info("subcriber - on InvoiceProcessedEvent");

        InvoiceProcessedEvent event = dispatchedEvent.getEvent();
        InvoiceInfo invoiceInfo = new InvoiceInfo(event.getSagaInfo().getSagaId(), event.getInvoice());
        invoiceService.sendInvoice(invoiceInfo);
    }

}
