package org.learn.eventuate.coreapi

//domain
data class OrderSagaInfo(val sagaId: String = "", val productInfo: ProductInfo? = null)
data class ShipmentInfo(val sagaId: String = "", val shipmentId: String = "", val shipmentPrice: Int = 0)
data class InvoiceInfo(val sagaId: String = "", val invoiceId: String = "", val invoice: String = "")

data class ParticipantFailureInfo(val sagaId: String = "", val id: String = "", val cause: String = "")
