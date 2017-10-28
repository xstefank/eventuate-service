package org.learn.eventuate.orderservice.domain;

import io.eventuate.Event;
import io.eventuate.EventUtil;
import io.eventuate.ReflectiveMutableCommandProcessingAggregate;
import org.learn.eventuate.coreapi.OrderCompletedEvent;
import org.learn.eventuate.coreapi.OrderFiledEvent;
import org.learn.eventuate.orderservice.command.FileOrderCommand;
import org.learn.eventuate.orderservice.command.OrderCommand;
import org.learn.eventuate.orderservice.command.OrderCompletedCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class OrderAggregate extends ReflectiveMutableCommandProcessingAggregate<OrderAggregate, OrderCommand> {

    private String orderId;

    private boolean completed;

    private static final Logger log = LoggerFactory.getLogger(OrderAggregate.class);

    public List<Event> process(FileOrderCommand command) {
        log.info("received FileOrderCommand");
        return EventUtil.events(new OrderFiledEvent(command.getOrderId(), command.getProductInfo()));
    }

    public List<Event> process(OrderCompletedCommand command) {
        log.info("received OrderCompletedCommand");
        return EventUtil.events(new OrderCompletedEvent(command.getOrderId(), command.getProductInfo()));
    }

    public void apply(OrderFiledEvent event) {
        log.info("on OrderFiledEvent");
        orderId = event.getOrderId();
    }

    public void apply(OrderCompletedEvent event) {
        log.info(String.format("Order %s completed", event.getOrderId()));
        this.completed = true;
    }

}
