package org.learn.eventuate.coreapi

import io.eventuate.Event
import io.eventuate.EventEntity

@EventEntity(entity = "org.learn.eventuate.invoiceservice.domain.InvoiceAggregate")
interface InvoiceEvent : Event

class InvoiceProcessedEvent(val id: String = "",
                            val orderSagaInfo: OrderSagaInfo? = null,
                            val invoice: String = "") : InvoiceEvent

class ConfirmInvoiceCompensationEvent(val sagaId: String = "", val invoiceId: String = "") : InvoiceEvent
class InvoicePreparationFailedEvent(val invoiceId: String = "", val sagaId: String = "", val cause: String = "") : InvoiceEvent
