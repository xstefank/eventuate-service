package org.learn.eventuate.queryservice.model;

import org.springframework.data.annotation.Id;

public class Invoice {

    @Id
    private String id;

    private String invoiceId;
    private String invoice;
    private boolean canceled;

    public Invoice(String invoiceId, String invoice) {
        this.invoiceId = invoiceId;
        this.invoice = invoice;
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public String getInvoice() {
        return invoice;
    }

    public boolean isCanceled() {
        return canceled;
    }

    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }

    @Override
    public String toString() {
        return String.format("id: %s, invoice: %d", invoiceId, invoice);
    }
}
