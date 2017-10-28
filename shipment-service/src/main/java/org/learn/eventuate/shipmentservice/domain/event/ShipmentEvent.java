package org.learn.eventuate.shipmentservice.domain.event;

import io.eventuate.Event;
import io.eventuate.EventEntity;

@EventEntity(entity = "org.learn.eventuate.shipmentservice.domain.ShipmentAggregate")
public interface ShipmentEvent extends Event {
}
