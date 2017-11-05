package org.learn.eventuate.orderservice.saga;

import io.eventuate.DispatchedEvent;
import io.eventuate.EventHandlerMethod;
import io.eventuate.EventSubscriber;
import org.learn.eventuate.coreapi.OrderFiledEvent;
import org.learn.eventuate.orderservice.domain.event.CompensateSagaEvent;
import org.learn.eventuate.orderservice.domain.event.InvoiceFailedEvent;
import org.learn.eventuate.orderservice.domain.event.InvoiceRequestedEvent;
import org.learn.eventuate.orderservice.domain.event.OrderSagaCreatedEvent;
import org.learn.eventuate.orderservice.domain.event.ShipmentFailedEvent;
import org.learn.eventuate.orderservice.domain.event.ShipmentRequestedEvent;
import org.learn.eventuate.orderservice.domain.service.OrderSagaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@EventSubscriber(id = "sagaEventSubscriber")
public class SagaEventSubscriber {

    private static final Logger log = LoggerFactory.getLogger(SagaEventSubscriber.class);

    @Autowired
    private OrderSagaService orderSagaService;


    @EventHandlerMethod
    public void onOrderFiledEvent(DispatchedEvent<OrderFiledEvent> dispatchedEvent) {
        //order is created - start saga
        log.info("Event listener is starting saga");
        OrderFiledEvent event = dispatchedEvent.getEvent();
        orderSagaService.startSaga(event.getOrderId(), event.getProductInfo());
    }

    @EventHandlerMethod
    public void onOrderSagaCreatedEvent(DispatchedEvent<OrderSagaCreatedEvent> dispatchedEvent) {
        log.info("on OrderSagaCreatedEvent");
        orderSagaService.requestShipment(dispatchedEvent.getEntityId(), dispatchedEvent.getEvent().getProductInfo());
        orderSagaService.requestShipment(dispatchedEvent.getEntityId(), dispatchedEvent.getEvent().getProductInfo());
    }

    @EventHandlerMethod
    public void onShipmentFailedEvent(DispatchedEvent<ShipmentFailedEvent> dispatchedEvent) {
        log.info("on ShipmentFailedEvent");
        orderSagaService.initSagaCompensation(dispatchedEvent.getEntityId(),
                dispatchedEvent.getEvent().getFailureInfo().getCause());
    }

    @EventHandlerMethod
    public void onInvoiceFailedEvent(DispatchedEvent<InvoiceFailedEvent> dispatchedEvent) {
        log.info("on InvoiceFailedEvent");
        orderSagaService.initSagaCompensation(dispatchedEvent.getEntityId(),
                dispatchedEvent.getEvent().getFailureInfo().getCause());
    }

    @EventHandlerMethod
    public void onCompensateSagaEvent(DispatchedEvent<CompensateSagaEvent> dispatchedEvent) {
        log.info("on CompensateSagaEvent");
        orderSagaService.compensateSaga(dispatchedEvent.getEntityId(), dispatchedEvent.getEvent());
    }
}
