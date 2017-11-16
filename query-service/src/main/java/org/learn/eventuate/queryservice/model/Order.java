package org.learn.eventuate.queryservice.model;

import org.learn.eventuate.coreapi.ProductInfo;
import org.springframework.data.annotation.Id;

public class Order {

    @Id
    private String id;

    private String orderId;
    private ProductInfo productInfo;

    public Order(String orderId, ProductInfo productInfo) {
        this.orderId = orderId;
        this.productInfo = productInfo;
    }

    public String getOrderId() {
        return orderId;
    }

    public ProductInfo getProductInfo() {
        return productInfo;
    }

    @Override
    public String toString() {
        return String.format("id: %s, product: %s", orderId, productInfo.getProductId());
    }
}
