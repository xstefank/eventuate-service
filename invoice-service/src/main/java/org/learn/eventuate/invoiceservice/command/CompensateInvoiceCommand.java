package org.learn.eventuate.invoiceservice.command;

import org.learn.eventuate.coreapi.ParticipantFailureInfo;

public class CompensateInvoiceCommand implements InvoiceCommand {

    private ParticipantFailureInfo failureInfo;

    public CompensateInvoiceCommand(ParticipantFailureInfo failureInfo) {
        this.failureInfo = failureInfo;
    }

    public ParticipantFailureInfo getFailureInfo() {
        return failureInfo;
    }
}
