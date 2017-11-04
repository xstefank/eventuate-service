package org.learn.eventuate.orderservice.domain.event;

import io.eventuate.Event;
import lombok.NoArgsConstructor;
import org.learn.eventuate.coreapi.ParticipantFailureInfo;

@NoArgsConstructor
public class InvoiceFailedEvent implements Event {
    private ParticipantFailureInfo failureInfo;

    public InvoiceFailedEvent(ParticipantFailureInfo failureInfo) {
        this.failureInfo = failureInfo;
    }

    public ParticipantFailureInfo getFailureInfo() {
        return failureInfo;
    }
}
