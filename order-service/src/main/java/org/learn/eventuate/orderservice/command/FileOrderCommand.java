package org.learn.eventuate.orderservice.command;

import org.learn.eventuate.coreapi.ProductInfo;

public class FileOrderCommand implements OrderCommand {

    private String orderId;

    private ProductInfo productInfo;

    public FileOrderCommand(String orderId, ProductInfo productInfo) {
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
