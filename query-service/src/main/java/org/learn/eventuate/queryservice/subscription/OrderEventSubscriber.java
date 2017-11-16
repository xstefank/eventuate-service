package org.learn.eventuate.queryservice.subscription;

import io.eventuate.DispatchedEvent;
import io.eventuate.EventHandlerMethod;
import io.eventuate.EventSubscriber;
import org.learn.eventuate.coreapi.OrderCompletedEvent;
import org.learn.eventuate.queryservice.model.Order;
import org.learn.eventuate.queryservice.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@EventSubscriber(id = "orderEventSubscriber")
public class OrderEventSubscriber {

    private static final Logger log = LoggerFactory.getLogger(OrderEventSubscriber.class);

    @Autowired
    private OrderRepository orderRepository;

    @EventHandlerMethod
    public void onOrderFiledEvent(DispatchedEvent<OrderCompletedEvent> dispatchedEvent) {
        OrderCompletedEvent event = dispatchedEvent.getEvent();
        orderRepository.save(new Order(event.getOrderId(), event.getProductInfo()));
    }

}
