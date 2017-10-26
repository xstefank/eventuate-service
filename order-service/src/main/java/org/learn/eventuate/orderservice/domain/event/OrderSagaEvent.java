package org.learn.eventuate.orderservice.domain.event;

import io.eventuate.Event;
import io.eventuate.EventEntity;

@EventEntity(entity = "org.learn.eventuate.orderservice.saga.OrderSagaAggregate")
public interface OrderSagaEvent extends Event {
}
