package org.learn.eventuate.orderservice.saga;

import io.eventuate.DispatchedEvent;
import io.eventuate.EventHandlerMethod;
import io.eventuate.EventSubscriber;
import org.learn.eventuate.coreapi.OrderFiledEvent;
import org.learn.eventuate.orderservice.domain.service.OrderSagaService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@EventSubscriber(id = "sagaEventSubscriber")
public class SagaEventSubscriber {

    @Autowired
    private OrderSagaService orderSagaService;

    @EventHandlerMethod
    public void on(DispatchedEvent<OrderFiledEvent> dispatchedEvent) {
        //order is created - start saga
        LoggerFactory.getLogger(SagaEventSubscriber.class).info("Event listener is startin saga");
        OrderFiledEvent event = dispatchedEvent.getEvent();
        orderSagaService.startSaga(event.getOrderId(), event.getProductInfo());
    }
}
