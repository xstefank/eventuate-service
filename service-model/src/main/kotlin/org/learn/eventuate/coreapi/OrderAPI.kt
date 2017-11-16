package org.learn.eventuate.coreapi

import io.eventuate.Event
import io.eventuate.EventEntity


//domain
data class ProductInfo(val productId: String = "", val comment: String = "", val price: Int = 0)


@EventEntity(entity = "org.learn.eventuate.orderservice.domain.OrderAggregate")
interface OrderEvent : Event

data class OrderFiledEvent(val orderId: String = "", val productInfo: ProductInfo? = null) : OrderEvent
class OrderCompletedEvent(val orderId: String = "", val productInfo: ProductInfo? = null) : OrderEvent
