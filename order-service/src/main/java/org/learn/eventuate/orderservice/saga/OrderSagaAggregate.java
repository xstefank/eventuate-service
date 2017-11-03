package org.learn.eventuate.orderservice.saga;

import io.eventuate.Event;
import io.eventuate.EventUtil;
import io.eventuate.ReflectiveMutableCommandProcessingAggregate;
import org.learn.eventuate.coreapi.ProductInfo;
import org.learn.eventuate.orderservice.command.saga.OrderSagaCommand;
import org.learn.eventuate.orderservice.command.saga.ProcessInvoiceCommand;
import org.learn.eventuate.orderservice.command.saga.ProcessShipmentCommand;
import org.learn.eventuate.orderservice.command.saga.ProcessShipmentFailureCommand;
import org.learn.eventuate.orderservice.command.saga.ShipmentCompensatedCommand;
import org.learn.eventuate.orderservice.command.saga.StartOrderSagaCommand;
import org.learn.eventuate.orderservice.domain.event.CompensateSagaEvent;
import org.learn.eventuate.orderservice.domain.event.InvoiceCompletedEvent;
import org.learn.eventuate.orderservice.domain.event.InvoiceRequestedEvent;
import org.learn.eventuate.orderservice.domain.event.OrderSagaCreatedEvent;
import org.learn.eventuate.orderservice.domain.event.ShipmentCompensatedEvent;
import org.learn.eventuate.orderservice.domain.event.ShipmentCompletedEvent;
import org.learn.eventuate.orderservice.domain.event.ShipmentRequestedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class OrderSagaAggregate extends ReflectiveMutableCommandProcessingAggregate<OrderSagaAggregate, OrderSagaCommand> {

    private static final Logger log = LoggerFactory.getLogger(OrderSagaAggregate.class.getSimpleName());

    private final OrderProcessing orderProcessing = new OrderProcessing();
    private final OrderCompensationProcessing compensationProcessing = new OrderCompensationProcessing();

    private String orderId;
    private ProductInfo productInfo;


    public List<Event> process(StartOrderSagaCommand command) {
        log.info("STARTING SAGA for order " + command.getOrderId());

        productInfo = command.getProductInfo();

        return EventUtil.events(new OrderSagaCreatedEvent(command.getOrderId(), command.getProductInfo()),
                new ShipmentRequestedEvent(productInfo),
                new InvoiceRequestedEvent(productInfo));
    }

    public List<Event> process(ProcessShipmentCommand command) {
        log.info("received ProcessShipmentCommand for order " + orderId);

        return EventUtil.events(new ShipmentCompletedEvent(command.getShipmentInfo().getShipmentId()));
    }

    public List<Event> process(ProcessInvoiceCommand command) {
        log.info("received ProcessInvoiceCommand for order " + orderId);

        return EventUtil.events(new InvoiceCompletedEvent());
    }

    public void apply(OrderSagaCreatedEvent event) {
        this.orderId = event.getOrderId();
        this.productInfo = event.getProductInfo();
    }

    public void apply(ShipmentRequestedEvent event) {
        log.info(String.format("Shipment for order %s has been requested", orderId));
    }

    public void apply(InvoiceRequestedEvent event) {
        log.info(String.format("Invoice for order %s has been requested", orderId));
    }

    public void apply(ShipmentCompletedEvent event) {
        orderProcessing.setShipmentId(event.getShipmentId());
        checkSagaCompleted();
    }

    public void apply(InvoiceCompletedEvent event) {
        orderProcessing.setInvoiceId("stub");
        checkSagaCompleted();
    }

    private void checkSagaCompleted() {
        if (orderProcessing.isDone()) {
            log.info("saga executed successfully");
            endSaga();
        }
    }


    //compensations

    public List<Event> process(ProcessShipmentFailureCommand command) {
        log.info("received ProcessShipmentFailureCommand for order " + orderId);

        return compensateSaga(command.getCause());
    }

    private List<Event> compensateSaga(String cause) {
        log.info(String.format("compensation of saga for model [%s] with casuse - %s", orderId, cause));

        return EventUtil.events(new CompensateSagaEvent(cause,
                orderProcessing.getShipmentId(), orderProcessing.getInvoiceId()));
    }

    public List<Event> process(ShipmentCompensatedCommand command) {
        return EventUtil.events(new ShipmentCompensatedEvent());
    }

    public void apply(CompensateSagaEvent event) {
        log.info("Saga for order " + orderId + " is being compensated");
    }

    public void apply(ShipmentCompensatedEvent event) {
        compensationProcessing.setInvoiceCompensated(true);
        checkSagaCompensated();
    }

    //    @SagaEventHandler(associationProperty = "orderId")
    //
    //    }
    //        compensateSaga(event.getOrderId(), event.getCause());
    //
    //        log.info("on InvoicePreparationFailedEvent");
//    public void on(InvoicePreparationFailedEvent event) {
//
//    @SagaEventHandler(associationProperty = "orderId")
//    public void on(ShipmentCompensatedEvent event) {
//        log.info("on ShipmentCompensatedEvent");
//        compensationProcessing.setShipmentCompensated(true);
//
//        checkSagaCompensated();
//    }
//
//    @SagaEventHandler(associationProperty = "orderId")
//    public void on(InvoiceCompensatedEvent event) {
//        log.info("on InvoiceCompensatedEvent");
//        compensationProcessing.setInvoiceCompensated(true);
//
//        checkSagaCompensated();
//    }
//
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

        public OrderProcessing() {
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

        public boolean isDone() {
            return shipmentId != null && invoiceId != null;
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
