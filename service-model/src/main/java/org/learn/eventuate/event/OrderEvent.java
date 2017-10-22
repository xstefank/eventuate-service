package org.learn.eventuate.event;

import io.eventuate.Event;
import io.eventuate.EventEntity;

@EventEntity(entity = "org.learn.eventuate.orderservice.domain.OrderAggregate")
public interface OrderEvent extends Event {
}
