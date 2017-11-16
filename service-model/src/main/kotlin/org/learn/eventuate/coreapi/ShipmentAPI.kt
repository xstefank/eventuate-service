package org.learn.eventuate.coreapi

import io.eventuate.Event
import io.eventuate.EventEntity

@EventEntity(entity = "org.learn.eventuate.shipmentservice.domain.ShipmentAggregate")
interface ShipmentEvent : Event

class ShipmentProcessedEvent(val id: String = "", val orderSagaInfo: OrderSagaInfo? = null, val price: Int = 0) : ShipmentEvent
class ConfirmCompensationEvent(val sagaId: String = "", val shipmentId: String = "") : ShipmentEvent
