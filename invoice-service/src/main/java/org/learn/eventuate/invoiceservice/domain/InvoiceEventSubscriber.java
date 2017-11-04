package org.learn.eventuate.invoiceservice.domain;

import io.eventuate.DispatchedEvent;
import io.eventuate.EventHandlerMethod;
import io.eventuate.EventSubscriber;
import org.learn.eventuate.coreapi.InvoiceInfo;
import org.learn.eventuate.invoiceservice.domain.event.ConfirmCompensationEvent;
import org.learn.eventuate.invoiceservice.domain.event.InvoicePreparationFailedEvent;
import org.learn.eventuate.invoiceservice.domain.event.InvoiceProcessedEvent;
import org.learn.eventuate.invoiceservice.domain.service.InvoiceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@EventSubscriber(id = "invoiceEventSubscriber")
public class InvoiceEventSubscriber {

    private static final Logger log = LoggerFactory.getLogger(InvoiceEventSubscriber.class);

    @Autowired
    private InvoiceService invoiceService;

    @EventHandlerMethod
    public void onInvoiceProcessedEvent(DispatchedEvent<InvoiceProcessedEvent> dispatchedEvent) {
        log.info("subcriber - on InvoiceProcessedEvent");

        InvoiceProcessedEvent event = dispatchedEvent.getEvent();
        InvoiceInfo invoiceInfo = new InvoiceInfo(event.getSagaInfo().getSagaId(),
                dispatchedEvent.getEntityId(), event.getInvoice());
        invoiceService.sendInvoice(invoiceInfo);
    }

    @EventHandlerMethod
    public void onConfirmCompensationEvent(DispatchedEvent<ConfirmCompensationEvent> dispatchedEvent) {
        log.info("subscriber - on ConfirmCompensationEvent");
        invoiceService.confirmCompensation(dispatchedEvent.getEvent().getSagaId());
    }

    @EventHandlerMethod
    public void onInvoicePreparationFailedEvent(DispatchedEvent<InvoicePreparationFailedEvent> dispatchedEvent) {
        log.info("subscriber - on InvoicePreparationFailedEvent");

        InvoicePreparationFailedEvent event = dispatchedEvent.getEvent();
        invoiceService.failInvoicePreparation(event.getSagaId(),
                dispatchedEvent.getEntityId(), event.getSagaId());
    }

}
