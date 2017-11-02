package org.learn.eventuate.orderservice.command.saga;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ProcessShipmentFailureCommand implements OrderSagaCommand {

    private String cause;

    public ProcessShipmentFailureCommand(String cause) {
        this.cause = cause;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }
}
