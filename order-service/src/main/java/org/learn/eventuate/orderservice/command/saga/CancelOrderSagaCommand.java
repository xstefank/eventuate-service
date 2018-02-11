package org.learn.eventuate.orderservice.command.saga;

public class CancelOrderSagaCommand implements OrderSagaCommand {

    private String orderId;

    public CancelOrderSagaCommand(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
