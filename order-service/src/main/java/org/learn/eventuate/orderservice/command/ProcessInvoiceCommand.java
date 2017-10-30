package org.learn.eventuate.orderservice.command;

import org.learn.eventuate.coreapi.InvoiceInfo;

public class ProcessInvoiceCommand implements OrderSagaCommand {
    private InvoiceInfo invoiceInfo;

    public ProcessInvoiceCommand(InvoiceInfo invoiceInfo) {
        this.invoiceInfo = invoiceInfo;
    }

    public InvoiceInfo getInvoiceInfo() {
        return invoiceInfo;
    }
}
