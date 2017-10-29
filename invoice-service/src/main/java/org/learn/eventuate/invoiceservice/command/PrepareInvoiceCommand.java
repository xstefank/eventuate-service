package org.learn.eventuate.invoiceservice.command;

import lombok.NoArgsConstructor;
import org.learn.eventuate.coreapi.OrderSagaInfo;

@NoArgsConstructor
public class PrepareInvoiceCommand implements InvoiceCommand {

    private OrderSagaInfo orderSagaInfo;

    public PrepareInvoiceCommand(OrderSagaInfo orderSagaInfo) {
        this.orderSagaInfo = orderSagaInfo;
    }

    public OrderSagaInfo getOrderSagaInfo() {
        return orderSagaInfo;
    }
}
