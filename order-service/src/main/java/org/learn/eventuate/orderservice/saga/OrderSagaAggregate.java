package org.learn.eventuate.orderservice.saga;

import io.eventuate.Event;
import io.eventuate.EventUtil;
import io.eventuate.ReflectiveMutableCommandProcessingAggregate;
import org.learn.eventuate.coreapi.ProductInfo;
import org.learn.eventuate.orderservice.command.saga.CompleteOrderSagaCommand;
import org.learn.eventuate.orderservice.command.saga.InitSagaCompensationCommand;
import org.learn.eventuate.orderservice.command.saga.InvoiceCompensatedCommand;
import org.learn.eventuate.orderservice.command.saga.OrderSagaCommand;
import org.learn.eventuate.orderservice.command.saga.ProcessInvoiceCommand;
import org.learn.eventuate.orderservice.command.saga.ProcessInvoiceFailureCommand;
import org.learn.eventuate.orderservice.command.saga.ProcessShipmentCommand;
import org.learn.eventuate.orderservice.command.saga.ProcessShipmentFailureCommand;
import org.learn.eventuate.orderservice.command.saga.ShipmentCompensatedCommand;
import org.learn.eventuate.orderservice.command.saga.StartOrderSagaCommand;
import org.learn.eventuate.orderservice.domain.event.CompensateSagaEvent;
import org.learn.eventuate.orderservice.domain.event.InvoiceCompensatedEvent;
import org.learn.eventuate.orderservice.domain.event.InvoiceCompletedEvent;
import org.learn.eventuate.orderservice.domain.event.InvoiceFailedEvent;
import org.learn.eventuate.orderservice.domain.event.OrderSagaComletedEvent;
import org.learn.eventuate.orderservice.domain.event.OrderSagaCreatedEvent;
import org.learn.eventuate.orderservice.domain.event.ShipmentCompensatedEvent;
import org.learn.eventuate.orderservice.domain.event.ShipmentCompletedEvent;
import org.learn.eventuate.orderservice.domain.event.ShipmentFailedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;

public class OrderSagaAggregate extends ReflectiveMutableCommandProcessingAggregate<OrderSagaAggregate, OrderSagaCommand> {

    private static final Logger log = LoggerFactory.getLogger(OrderSagaAggregate.class.getSimpleName());

    private final OrderProcessing orderProcessing = new OrderProcessing();
    private final OrderCompensationProcessing compensationProcessing = new OrderCompensationProcessing();

    private String orderId;
    private ProductInfo productInfo;

    public List<Event> process(StartOrderSagaCommand command) {
        log.info("STARTING SAGA for order aggregate " + command.getOrderId());
        productInfo = command.getProductInfo();

        return EventUtil.events(new OrderSagaCreatedEvent(command.getOrderId(), command.getProductInfo()));
    }

    public List<Event> process(ProcessShipmentCommand command) {
        log.info("received ProcessShipmentCommand for order " + orderId);
        return EventUtil.events(new ShipmentCompletedEvent(command.getShipmentInfo().getShipmentId()));
    }

    public List<Event> process(ProcessInvoiceCommand command) {
        log.info("received ProcessInvoiceCommand for order " + orderId);
        return EventUtil.events(new InvoiceCompletedEvent(command.getInvoiceInfo().getInvoiceId()));
    }

    public List<Event> process(CompleteOrderSagaCommand command) {
        if (orderProcessing.isDone()) {
            log.info("saga executed successfully");
            endSaga();
            return EventUtil.events(new OrderSagaComletedEvent(orderId));
        }

        return Collections.emptyList();
    }

    public void apply(OrderSagaCreatedEvent event) {
        this.orderId = event.getOrderId();
        this.productInfo = event.getProductInfo();
    }

    public void apply(ShipmentCompletedEvent event) {
        orderProcessing.setShipmentId(event.getShipmentId());
    }

    public void apply(InvoiceCompletedEvent event) {
        orderProcessing.setInvoiceId(event.getInvoiceId());
    }

    public void apply(OrderSagaComletedEvent event) {

    }

    //compensations

    public List<Event> process(ProcessShipmentFailureCommand command) {
        log.info("received ProcessShipmentFailureCommand for order " + orderId);
        return EventUtil.events(new ShipmentFailedEvent(command.getFailureInfo()));
    }

    public List<Event> process(ProcessInvoiceFailureCommand command) {
        log.info("received ProcessInvoiceFailureCommand for order " + orderId);
        return EventUtil.events(new InvoiceFailedEvent(command.getFailureInfo()));
    }

    public void apply(ShipmentFailedEvent event) {
        orderProcessing.markInvalid();
        orderProcessing.setShipmentId(event.getFailureInfo().getId());
    }

    public void apply(InvoiceFailedEvent event) {
        orderProcessing.markInvalid();
        orderProcessing.setInvoiceId(event.getFailureInfo().getId());
    }

    public List<Event> process(InitSagaCompensationCommand command) {
        log.info(String.format("intializing saga compensation for saga [%s] with cause - %s",
                command.getSagaId(), command.getCause()));

        return EventUtil.events(new CompensateSagaEvent(command.getCause(),
                orderProcessing.getShipmentId(), orderProcessing.getInvoiceId()));
    }

    public List<Event> process(ShipmentCompensatedCommand command) {
        log.info("received ShipmentCompensatedCommand for order " + orderId);
        return EventUtil.events(new ShipmentCompensatedEvent());
    }

    public List<Event> process(InvoiceCompensatedCommand command) {
        log.info("received InvoiceCompensatedCommand for order " + orderId);
        return EventUtil.events(new InvoiceCompensatedEvent());
    }

    public void apply(CompensateSagaEvent event) {
    }

    public void apply(ShipmentCompensatedEvent event) {
        compensationProcessing.setShipmentCompensated(true);
        checkSagaCompensated();
    }

    public void apply(InvoiceCompensatedEvent event) {
        compensationProcessing.setInvoiceCompensated(true);
        checkSagaCompensated();
    }

    private void checkSagaCompensated() {
        if (compensationProcessing.isCompensated()) {
            log.info("saga fully compensated");
            endSaga();
        }
    }

    private void endSaga() {
        log.info("ENDING SAGA");
    }

    private static class OrderProcessing {

        private String shipmentId;
        private String invoiceId;
        private boolean valid;

        public OrderProcessing() {
            this.valid = true;
        }

        public String getShipmentId() {
            return shipmentId;
        }

        public void setShipmentId(String shipmentId) {
            this.shipmentId = shipmentId;
        }

        public String getInvoiceId() {
            return invoiceId;
        }

        public void setInvoiceId(String invoiceId) {
            this.invoiceId = invoiceId;
        }

        public boolean isValid() {
            return valid;
        }

        public void markInvalid() {
            this.valid = false;
        }

        public boolean isDone() {
            return isValid() && shipmentId != null && invoiceId != null;
        }
    }

    private static class OrderCompensationProcessing {

        private boolean shipmentCompensated;
        private boolean invoiceCompensated;

        public void setShipmentCompensated(boolean shipmentCompensated) {
            this.shipmentCompensated = shipmentCompensated;
        }

        public void setInvoiceCompensated(boolean invoiceCompensated) {
            this.invoiceCompensated = invoiceCompensated;
        }

        public boolean isCompensated() {
            return shipmentCompensated && invoiceCompensated;
        }
    }

}
