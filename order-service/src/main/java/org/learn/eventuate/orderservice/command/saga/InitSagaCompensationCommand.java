package org.learn.eventuate.orderservice.command.saga;

public class InitSagaCompensationCommand implements OrderSagaCommand {
    private String sagaId;
    private String cause;

    public InitSagaCompensationCommand(String sagaId, String cause) {
        this.sagaId = sagaId;
        this.cause = cause;
    }

    public String getSagaId() {
        return sagaId;
    }

    public String getCause() {
        return cause;
    }
}
