package org.learn.eventuate.orderservice.domain.model;

import lombok.NoArgsConstructor;
import org.learn.eventuate.coreapi.ProductInfo;

@NoArgsConstructor
public class OrderInfo {

    private String id;
    private ProductInfo productInfo;

    public OrderInfo(String id, ProductInfo productInfo) {
        this.id = id;
        this.productInfo = productInfo;
    }

    public String getId() {
        return id;
    }

    public ProductInfo getProductInfo() {
        return productInfo;
    }
}
