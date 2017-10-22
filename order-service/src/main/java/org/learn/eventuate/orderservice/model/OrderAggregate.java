package org.learn.eventuate.orderservice.model;

import io.eventuate.Event;
import io.eventuate.EventUtil;
import org.learn.eventuate.coreapi.OrderFiledEvent;
import org.learn.eventuate.orderservice.command.FileOrderCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class OrderAggregate {

    private String orderId;

    private boolean completed;

    private static final Logger log = LoggerFactory.getLogger(OrderAggregate.class);

    public List<Event> process(FileOrderCommand command) {
        log.info("received FileOrderCommand");
        return EventUtil.events(new OrderFiledEvent(command.getOrderId(), command.getProductInfo()));
    }
//
//    @CommandHandler
//    public void handle(OrderCompletedCommand command) {
//        apply(new OrderCompletedEvent(command.getOrderId(), command.getProductInfo()));
//    }
//
//    @EventSourcingHandler
//    public void on(OrderFiledEvent event) {
//        orderId = event.getOrderId();
//    }

}
