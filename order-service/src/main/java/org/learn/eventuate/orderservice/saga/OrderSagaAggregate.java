package org.learn.eventuate.orderservice.saga;

import io.eventuate.EventSubscriber;
import io.eventuate.ReflectiveMutableCommandProcessingAggregate;
import org.learn.eventuate.coreapi.ProductInfo;
import org.learn.eventuate.orderservice.command.OrderSagaCommand;
import org.learn.eventuate.orderservice.command.ProcessShipmentCommand;
import org.learn.eventuate.orderservice.command.StartOrderSagaCommand;
import org.learn.eventuate.orderservice.domain.service.ShipmentService;
import org.learn.eventuate.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@EventSubscriber(id = "orderManagementSagaEvents")
public class OrderSagaAggregate extends ReflectiveMutableCommandProcessingAggregate<OrderSagaAggregate, OrderSagaCommand> {

    private static final Logger log = LoggerFactory.getLogger(OrderSagaAggregate.class.getSimpleName());

    private final OrderProcessing orderProcessing = new OrderProcessing();
    private final OrderCompensationProcessing compensationProcessing = new OrderCompensationProcessing();

    private String id;
    private ProductInfo productInfo;

    @Autowired
    private ShipmentService shipmentService;


    public void process(StartOrderSagaCommand command) {
        id = Util.generateId();
        log.info("STARTING SAGA - " + id);


        productInfo = command.getProductInfo();

        shipmentService.requestShipment(id, productInfo);

//        //request shipment
//        log.info("sending PrepareShipmentCommand");
//        commandGateway.send(new PrepareShipmentCommand(orderId, productInfo));
//
//        //request invoice
//        log.info("sending PrepareInvoiceCommand");
//        commandGateway.send(new PrepareInvoiceCommand(orderId, productInfo));

    }

    public void process(ProcessShipmentCommand command) {
        log.info("received ProcessShipmentCommand for saga " + id);
        orderProcessing.setShipmentProcessed(true);

//        checkSagaCompleted();
    }
//
//    @SagaEventHandler(associationProperty = "orderId")
//    public void on(InvoicePreparedEvent event) {
//        log.info("on InvoicePreparedEvent");
//        orderProcessing.setInvoiceProcessed(true);
//
//        checkSagaCompleted();
//    }
//
//    private void checkSagaCompleted() {
//        if (orderProcessing.isDone()) {
//            log.info("saga executed successfully");
//            endSaga();
//            commandGateway.send(new OrderCompletedCommand(orderId, productInfo));
//        }
//    }
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
//    @EndSaga
//    private void endSaga() {
//        log.info("ENDING SAGA");
//    }

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
