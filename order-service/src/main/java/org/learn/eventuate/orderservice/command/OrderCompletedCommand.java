package org.learn.eventuate.orderservice.command;

import org.learn.eventuate.coreapi.ProductInfo;

public class OrderCompletedCommand implements OrderCommand {

    private String orderId;

    private ProductInfo productInfo;

    public OrderCompletedCommand() {
    }

    public OrderCompletedCommand(String orderId, ProductInfo productInfo) {
        this.orderId = orderId;
        this.productInfo = productInfo;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public ProductInfo getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(ProductInfo productInfo) {
        this.productInfo = productInfo;
    }
}
