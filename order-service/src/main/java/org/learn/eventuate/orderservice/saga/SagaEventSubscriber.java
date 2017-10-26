package org.learn.eventuate.orderservice.saga;

import io.eventuate.DispatchedEvent;
import io.eventuate.EventHandlerMethod;
import io.eventuate.EventSubscriber;
import org.learn.eventuate.coreapi.OrderFiledEvent;
import org.learn.eventuate.orderservice.domain.event.ShipmentRequestedEvent;
import org.learn.eventuate.orderservice.domain.service.OrderSagaService;
import org.learn.eventuate.orderservice.domain.service.ShipmentService;
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

    @Autowired
    private ShipmentService shipmentService;


    @EventHandlerMethod
    public void onOrderFiledEvent(DispatchedEvent<OrderFiledEvent> dispatchedEvent) {
        //order is created - start saga
        log.info("Event listener is startin saga");
        OrderFiledEvent event = dispatchedEvent.getEvent();
        orderSagaService.startSaga(event.getOrderId(), event.getProductInfo());
    }


    @EventHandlerMethod
    public void onShipmentRequestedEvent(DispatchedEvent<ShipmentRequestedEvent> dispatchedEvent) {
        log.info("on ShipmentRequestedEvent");
        shipmentService.requestShipment(dispatchedEvent.getEntityId(), dispatchedEvent.getEvent().getOrderInfo());
    }
}
