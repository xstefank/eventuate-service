package org.learn.eventuate.invoiceservice.domain;

import io.eventuate.Event;
import io.eventuate.EventUtil;
import io.eventuate.ReflectiveMutableCommandProcessingAggregate;
import org.learn.eventuate.coreapi.OrderSagaInfo;
import org.learn.eventuate.coreapi.ProductInfo;
import org.learn.eventuate.invoiceservice.command.InvoiceCommand;
import org.learn.eventuate.invoiceservice.command.PrepareInvoiceCommand;
import org.learn.eventuate.invoiceservice.domain.event.InvoiceProcessedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class InvoiceAggregate extends ReflectiveMutableCommandProcessingAggregate<InvoiceAggregate, InvoiceCommand> {

    private Logger log = LoggerFactory.getLogger(InvoiceAggregate.class);

    private String invoice;

    public List<Event> process(PrepareInvoiceCommand command) {
        log.info("on PrepareInvoiceCommand");
        OrderSagaInfo sagaInfo = command.getOrderSagaInfo();

        String invoice = generateInvoice(sagaInfo.getProductInfo());
        return EventUtil.events(new InvoiceProcessedEvent(sagaInfo, invoice));
    }

    public void apply(InvoiceProcessedEvent event) {
        log.info("on InvoiceProcessedEvent");
        this.invoice = event.getInvoice();
    }

    private String generateInvoice(ProductInfo productInfo) {
        //return stub
        return "This is not the invoice you are looking for...";
    }

}
