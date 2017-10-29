package org.learn.eventuate.invoiceservice.domain.event;

import io.eventuate.Event;
import io.eventuate.EventEntity;

@EventEntity(entity = "org.learn.eventuate.domain.InvoiceAggregate")
public interface InvoiceEvent extends Event {
}
