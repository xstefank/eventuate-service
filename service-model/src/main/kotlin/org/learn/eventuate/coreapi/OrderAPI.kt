package org.learn.eventuate.coreapi

import org.learn.eventuate.event.OrderEvent


//domain
data class ProductInfo(val productId: String = "", val comment: String = "", val price: Int = 0)

data class OrderSagaInfo(val sagaId: String = "", val productInfo: ProductInfo? = null)
data class ShipmentInfo(val sagaId: String = "", val shipmentId: String = "", val shipmentPrice: Int = 0)
data class InvoiceInfo(val sagaId: String = "", val invoiceId: String = "", val invoice: String = "")

data class FailureInfo(val sagaId: String = "", val cause: String = "")
data class ParticipantFailureInfo(val sagaId: String = "", val id: String = "", val cause: String = "")


data class OrderFiledEvent(val orderId: String = "", val productInfo: ProductInfo? = null) : OrderEvent
class OrderCompletedEvent(val orderId: String, val productInfo: ProductInfo) : OrderEvent
