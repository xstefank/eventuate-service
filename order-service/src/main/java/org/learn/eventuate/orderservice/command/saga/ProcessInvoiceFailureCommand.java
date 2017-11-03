package org.learn.eventuate.orderservice.command.saga;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ProcessInvoiceFailureCommand implements OrderSagaCommand {
    private String cause;

    public ProcessInvoiceFailureCommand(String cause) {
        this.cause = cause;
    }

    public String getCause() {
        return cause;
    }
}
