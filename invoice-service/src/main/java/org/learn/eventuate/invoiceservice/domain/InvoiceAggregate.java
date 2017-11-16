package org.learn.eventuate.invoiceservice.domain;

import io.eventuate.Event;
import io.eventuate.EventUtil;
import io.eventuate.ReflectiveMutableCommandProcessingAggregate;
import org.learn.eventuate.coreapi.ConfirmInvoiceCompensationEvent;
import org.learn.eventuate.coreapi.InvoicePreparationFailedEvent;
import org.learn.eventuate.coreapi.InvoiceProcessedEvent;
import org.learn.eventuate.coreapi.OrderSagaInfo;
import org.learn.eventuate.coreapi.ProductInfo;
import org.learn.eventuate.invoiceservice.command.CompensateInvoiceCommand;
import org.learn.eventuate.invoiceservice.command.InvoiceCommand;
import org.learn.eventuate.invoiceservice.command.PrepareInvoiceCommand;
import org.learn.eventuate.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class InvoiceAggregate extends ReflectiveMutableCommandProcessingAggregate<InvoiceAggregate, InvoiceCommand> {

    private static final Logger log = LoggerFactory.getLogger(InvoiceAggregate.class);

    private String id;
    private String invoice;
    private boolean deleted;

    public List<Event> process(PrepareInvoiceCommand command) {
        log.info("on PrepareInvoiceCommand");

        id = Util.generateId();
        OrderSagaInfo sagaInfo = command.getOrderSagaInfo();

        if (sagaInfo.getProductInfo().getProductId().equals("failInvoice")) {
            return EventUtil.events(new InvoicePreparationFailedEvent(id, sagaInfo.getSagaId(), "test stub invoice failure"));
        }

        String invoice = generateInvoice(sagaInfo.getProductInfo());
        return EventUtil.events(new InvoiceProcessedEvent(id, sagaInfo, invoice));
    }

    public List<Event> process(CompensateInvoiceCommand command) {

        //invoice compensation
        log.info("invoice " + command.getFailureInfo().getId() + " compensated");

        return EventUtil.events(new ConfirmInvoiceCompensationEvent(command.getFailureInfo().getSagaId(), id));
    }

    public void apply(InvoiceProcessedEvent event) {
        this.id = event.getId();
        this.invoice = event.getInvoice();
    }

    public void apply(InvoicePreparationFailedEvent event) {
        this.id = event.getInvoiceId();
        this.invoice = "N/A";
    }

    public void apply(ConfirmInvoiceCompensationEvent event) {
        this.deleted = true;
    }

    private String generateInvoice(ProductInfo productInfo) {
        //return stub
        return "This is not the invoice you are looking for...";
    }

}
