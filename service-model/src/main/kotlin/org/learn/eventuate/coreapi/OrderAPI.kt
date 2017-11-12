package org.learn.eventuate.coreapi

import io.eventuate.Event
import io.eventuate.EventEntity


//domain
data class ProductInfo(val productId: String = "", val comment: String = "", val price: Int = 0)

data class OrderSagaInfo(val sagaId: String = "", val productInfo: ProductInfo? = null)
data class ShipmentInfo(val sagaId: String = "", val shipmentId: String = "", val shipmentPrice: Int = 0)
data class InvoiceInfo(val sagaId: String = "", val invoiceId: String = "", val invoice: String = "")

data class ParticipantFailureInfo(val sagaId: String = "", val id: String = "", val cause: String = "")


//---------------

@EventEntity(entity = "org.learn.eventuate.orderservice.domain.OrderAggregate")
interface OrderEvent : Event


data class OrderFiledEvent(val orderId: String = "", val productInfo: ProductInfo? = null) : OrderEvent
class OrderCompletedEvent(val orderId: String = "", val productInfo: ProductInfo? = null) : OrderEvent
