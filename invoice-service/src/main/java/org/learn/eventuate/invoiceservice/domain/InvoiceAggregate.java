package org.learn.eventuate.invoiceservice.domain;

import io.eventuate.Event;
import io.eventuate.EventUtil;
import io.eventuate.ReflectiveMutableCommandProcessingAggregate;
import org.learn.eventuate.coreapi.OrderSagaInfo;
import org.learn.eventuate.coreapi.ProductInfo;
import org.learn.eventuate.invoiceservice.command.CompensateInvoiceCommand;
import org.learn.eventuate.invoiceservice.command.InvoiceCommand;
import org.learn.eventuate.invoiceservice.command.PrepareInvoiceCommand;
import org.learn.eventuate.invoiceservice.domain.event.ConfirmCompensationEvent;
import org.learn.eventuate.invoiceservice.domain.event.InvoicePreparationFailedEvent;
import org.learn.eventuate.invoiceservice.domain.event.InvoiceProcessedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class InvoiceAggregate extends ReflectiveMutableCommandProcessingAggregate<InvoiceAggregate, InvoiceCommand> {

    private static final Logger log = LoggerFactory.getLogger(InvoiceAggregate.class);

    private String invoice;
    private boolean deleted;

    public List<Event> process(PrepareInvoiceCommand command) {
        log.info("on PrepareInvoiceCommand");
        OrderSagaInfo sagaInfo = command.getOrderSagaInfo();

        if (sagaInfo.getProductInfo().getProductId().equals("failInvoice")) {
            return EventUtil.events(new InvoicePreparationFailedEvent(sagaInfo.getSagaId(), "test stub invoice failure"));
        }

        String invoice = generateInvoice(sagaInfo.getProductInfo());
        return EventUtil.events(new InvoiceProcessedEvent(sagaInfo, invoice));
    }

    public List<Event> process(CompensateInvoiceCommand command) {

        //invoice compensation
        log.info("invoice " + command.getFailureInfo().getId() + " compensated");

        return EventUtil.events(new ConfirmCompensationEvent(command.getFailureInfo().getSagaId()));
    }

    public void apply(InvoiceProcessedEvent event) {
        log.info("on InvoiceProcessedEvent");
        this.invoice = event.getInvoice();
    }

    public void apply(InvoicePreparationFailedEvent event) {
        log.info("invoice preparation failed with cause " + event.getCause());
    }

    public void apply(ConfirmCompensationEvent event) {
        this.deleted = true;
    }

    private String generateInvoice(ProductInfo productInfo) {
        //return stub
        return "This is not the invoice you are looking for...";
    }

}
