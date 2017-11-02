package org.learn.eventuate.orderservice.command.saga;

import org.learn.eventuate.coreapi.ProductInfo;

public class StartOrderSagaCommand implements OrderSagaCommand {

    private String orderId;
    private ProductInfo productInfo;

    public StartOrderSagaCommand(String orderId, ProductInfo productInfo) {
        this.orderId = orderId;
        this.productInfo = productInfo;
    }

    public String getOrderId() {
        return orderId;
    }

    public ProductInfo getProductInfo() {
        return productInfo;
    }
}
