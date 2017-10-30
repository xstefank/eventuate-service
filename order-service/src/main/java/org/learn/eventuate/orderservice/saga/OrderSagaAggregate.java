package org.learn.eventuate.orderservice.saga;

import io.eventuate.Event;
import io.eventuate.EventUtil;
import io.eventuate.ReflectiveMutableCommandProcessingAggregate;
import org.learn.eventuate.coreapi.ProductInfo;
import org.learn.eventuate.orderservice.command.OrderSagaCommand;
import org.learn.eventuate.orderservice.command.ProcessInvoiceCommand;
import org.learn.eventuate.orderservice.command.ProcessShipmentCommand;
import org.learn.eventuate.orderservice.command.StartOrderSagaCommand;
import org.learn.eventuate.orderservice.domain.event.InvoiceRequestedEvent;
import org.learn.eventuate.orderservice.domain.event.OrderSagaComletedEvent;
import org.learn.eventuate.orderservice.domain.event.OrderSagaCreatedEvent;
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

//        shipmentService.requestShipment(id, productInfo);

//        //request shipment
//        log.info("sending PrepareShipmentCommand");
//        commandGateway.send(new PrepareShipmentCommand(orderId, productInfo));
//
//        //request invoice
//        log.info("sending PrepareInvoiceCommand");
//        commandGateway.send(new PrepareInvoiceCommand(orderId, productInfo));

        return EventUtil.events(new OrderSagaCreatedEvent(command.getOrderId(), command.getProductInfo()),
                new ShipmentRequestedEvent(productInfo),
                new InvoiceRequestedEvent(productInfo));
    }

    public List<Event> process(ProcessShipmentCommand command) {
        log.info("received ProcessShipmentCommand for order " + orderId);
        orderProcessing.setShipmentProcessed(true);

        return checkSagaCompleted();
    }

    public List<Event> process(ProcessInvoiceCommand command) {
        log.info("received ProcessInvoiceCommand for order " + orderId);
        orderProcessing.setInvoiceProcessed(true);

        return checkSagaCompleted();
    }

    public void apply(OrderSagaCreatedEvent event) {
        this.orderId = event.getOrderId();
        this.productInfo = event.getProductInfo();
    }

    //required by eventuate
    
    public void apply(ShipmentRequestedEvent event) {
        log.info(String.format("Shipment for order %s has been requested", orderId));
    }

    public void apply(InvoiceRequestedEvent event) {
        log.info(String.format("Invoice for order %s has been requested", orderId));
    }

    private List<Event> checkSagaCompleted() {
        if (orderProcessing.isDone()) {
            log.info("saga executed successfully");
            endSaga();
            return EventUtil.events(new OrderSagaComletedEvent(orderId));
        }

        return EventUtil.events();
    }

    public void apply(OrderSagaComletedEvent event) {
        log.info("Saga ENDED");
    }
//
//    //compensations
//
//    @SagaEventHandler(associationProperty = "orderId")
//    public void on(ShipmentPreparationFailedEvent event) {
//        log.info("on ShipmentPreparationFailedEvent");
//
//        compensateSaga(event.getOrderId(), event.getCause());
//    }
//
//    @SagaEventHandler(associationProperty = "orderId")
//    public void on(InvoicePreparationFailedEvent event) {
//        log.info("on InvoicePreparationFailedEvent");
//
//        compensateSaga(event.getOrderId(), event.getCause());
//    }
//
//    private void compensateSaga(String orderId, String cause) {
//        log.info(String.format("compensation of saga for model [%s] with casuse - %s", orderId, cause));
//        commandGateway.send(new CompensateShipmentCommand(orderId, cause));
//        commandGateway.send(new CompensateInvoiceCommand(orderId, cause));
//    }
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
//    private void checkSagaCompensated() {
//        if (compensationProcessing.isCompensated()) {
//            log.info("saga fully compensated");
//            endSaga();
//        }
//    }
//

    private void endSaga() {
        log.info("ENDING SAGA");
    }

    private static class OrderProcessing {

        private boolean shipmentProcessed;
        private boolean invoiceProcessed;

        public OrderProcessing() {
        }

        public void setShipmentProcessed(boolean shipmentProcessed) {
            this.shipmentProcessed = shipmentProcessed;
        }

        public void setInvoiceProcessed(boolean invoiceProcessed) {
            this.invoiceProcessed = invoiceProcessed;
        }

        public boolean isDone() {
            return shipmentProcessed && invoiceProcessed;
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
