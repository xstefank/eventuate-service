package org.learn.eventuate.queryservice.subscription;

import io.eventuate.DispatchedEvent;
import io.eventuate.EventHandlerMethod;
import io.eventuate.EventSubscriber;
import org.learn.eventuate.coreapi.ConfirmInvoiceCompensationEvent;
import org.learn.eventuate.coreapi.InvoicePreparationFailedEvent;
import org.learn.eventuate.coreapi.InvoiceProcessedEvent;
import org.learn.eventuate.queryservice.model.Invoice;
import org.learn.eventuate.queryservice.repository.InvoiceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@EventSubscriber(id = "invoiceQuerySubscriber")
public class InvoiceQuerySubscriber {

    private static final Logger log = LoggerFactory.getLogger(InvoiceQuerySubscriber.class);

    @Autowired
    private InvoiceRepository invoiceRepository;

    @EventHandlerMethod
    public void onInvoiceProcessedEvent(DispatchedEvent<InvoiceProcessedEvent> dispatchedEvent) {
        log.info("on InvoiceProcessedEvent");

        InvoiceProcessedEvent event = dispatchedEvent.getEvent();
        invoiceRepository.save(new Invoice(event.getId(), event.getInvoice()));
    }

    @EventHandlerMethod
    public void onConfirmInvoiceCompensationEvent(DispatchedEvent<ConfirmInvoiceCompensationEvent> dispatchedEvent) {
        ConfirmInvoiceCompensationEvent event = dispatchedEvent.getEvent();

        Invoice invoice = invoiceRepository.findByInvoiceId(event.getInvoiceId());
        invoice.setCanceled(true);
        invoiceRepository.save(invoice);
    }

    @EventHandlerMethod
    public void onInvoicePreparationFailedEvent(DispatchedEvent<InvoicePreparationFailedEvent> dispatchedEvent) {
        InvoicePreparationFailedEvent event = dispatchedEvent.getEvent();
        invoiceRepository.save(new Invoice(event.getInvoiceId(), "N/A"));
    }


}
