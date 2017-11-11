package org.learn.eventuate.queryservice.subscribtion;

import io.eventuate.DispatchedEvent;
import io.eventuate.EventHandlerMethod;
import io.eventuate.EventSubscriber;
import org.learn.eventuate.coreapi.OrderFiledEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@EventSubscriber(id = "orderEventSubscriber")
public class OrderEventSubscriber {

    private static final Logger log = LoggerFactory.getLogger(OrderEventSubscriber.class);

    @EventHandlerMethod
    public void onOrderFiledEvent(DispatchedEvent<OrderFiledEvent> dispatchedEvent) {
        log.info("on OrderFiledEventXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
    }

}
