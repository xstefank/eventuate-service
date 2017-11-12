package org.learn.eventuate.orderservice.saga;

import io.eventuate.DispatchedEvent;
import io.eventuate.EventHandlerMethod;
import io.eventuate.EventSubscriber;
import org.learn.eventuate.coreapi.OrderFiledEvent;
import org.learn.eventuate.orderservice.domain.event.CompensateSagaEvent;
import org.learn.eventuate.orderservice.domain.event.InvoiceCompletedEvent;
import org.learn.eventuate.orderservice.domain.event.InvoiceFailedEvent;
import org.learn.eventuate.orderservice.domain.event.OrderSagaComletedEvent;
import org.learn.eventuate.orderservice.domain.event.OrderSagaCreatedEvent;
import org.learn.eventuate.orderservice.domain.event.ShipmentCompletedEvent;
import org.learn.eventuate.orderservice.domain.event.ShipmentFailedEvent;
import org.learn.eventuate.orderservice.domain.service.OrderSagaService;
import org.learn.eventuate.orderservice.domain.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@EventSubscriber(id = "sagaEventSubscriber")
public class SagaEventSubscriber {

    private static final Logger log = LoggerFactory.getLogger(SagaEventSubscriber.class);

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderSagaService orderSagaService;


    @EventHandlerMethod
    public void onOrderFiledEvent(DispatchedEvent<OrderFiledEvent> dispatchedEvent) {
        OrderFiledEvent event = dispatchedEvent.getEvent();
        orderSagaService.startSaga(dispatchedEvent.getEntityId(), event.getProductInfo());
    }

    @EventHandlerMethod
    public void onOrderSagaCreatedEvent(DispatchedEvent<OrderSagaCreatedEvent> dispatchedEvent) {
        orderSagaService.requestShipment(dispatchedEvent.getEntityId(), dispatchedEvent.getEvent().getProductInfo());
        orderSagaService.requestInvoice(dispatchedEvent.getEntityId(), dispatchedEvent.getEvent().getProductInfo());
    }

    @EventHandlerMethod
    public void onShipmentCompletedEvent(DispatchedEvent<ShipmentCompletedEvent> dispatchedEvent) {
        orderSagaService.completeSaga(dispatchedEvent.getEntityId());
    }

    @EventHandlerMethod
    public void onInvoiceCompletedEvent(DispatchedEvent<InvoiceCompletedEvent> dispatchedEvent) {
        orderSagaService.completeSaga(dispatchedEvent.getEntityId());
    }

    @EventHandlerMethod
    public void onOrderSagaCompletedEvent(DispatchedEvent<OrderSagaComletedEvent> dispatchedEvent) {
        orderService.complete(dispatchedEvent.getEvent().getOrderId());
    }

    @EventHandlerMethod
    public void onShipmentFailedEvent(DispatchedEvent<ShipmentFailedEvent> dispatchedEvent) {
        orderSagaService.initSagaCompensation(dispatchedEvent.getEntityId(),
                dispatchedEvent.getEvent().getFailureInfo().getCause());
    }

    @EventHandlerMethod
    public void onInvoiceFailedEvent(DispatchedEvent<InvoiceFailedEvent> dispatchedEvent) {
        orderSagaService.initSagaCompensation(dispatchedEvent.getEntityId(),
                dispatchedEvent.getEvent().getFailureInfo().getCause());
    }

    @EventHandlerMethod
    public void onCompensateSagaEvent(DispatchedEvent<CompensateSagaEvent> dispatchedEvent) {
        orderSagaService.compensateSaga(dispatchedEvent.getEntityId(), dispatchedEvent.getEvent());
    }
}
