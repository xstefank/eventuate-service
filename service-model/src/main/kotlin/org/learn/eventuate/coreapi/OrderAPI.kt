package org.learn.eventuate.coreapi

import org.learn.eventuate.event.OrderEvent


//domain
data class ProductInfo(val productId: String = "", val comment: String = "", val price: Int = 0)


data class ShipmentInfo(val sagaId: String = "", val shipmentPrice: Int = 0)


data class OrderFiledEvent(val orderId: String = "", val productInfo: ProductInfo? = null) : OrderEvent
class OrderCompletedEvent(val orderId: String, val productInfo: ProductInfo) : OrderEvent


class PrepareShipmentCommand(val orderId: String, val productInfo: ProductInfo)
class ShipmentPreparedEvent(val shipmentId: String, val orderId: String, val price: Int)
class ShipmentPreparationFailedEvent(val shipmentId: String, val orderId: String, val cause: String)


class PrepareInvoiceCommand(val orderId: String, val productInfo: ProductInfo)
class InvoicePreparedEvent(val invoiceId: String, val orderId: String, val invoice: String)
class InvoicePreparationFailedEvent(val invoiceId: String, val orderId: String, val cause: String)


//compensation

class CompensateShipmentCommand(val orderId: String, val cause: String)
class ShipmentCompensatedEvent(val shipmentId: String, val orderId: String, val description: String)


class CompensateInvoiceCommand(val orderId: String, val cause: String)
class InvoiceCompensatedEvent(val invoiceId: String ,val orderId: String, val description: String)
