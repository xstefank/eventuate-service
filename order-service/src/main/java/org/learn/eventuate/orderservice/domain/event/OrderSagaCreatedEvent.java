package org.learn.eventuate.orderservice.domain.event;

import io.eventuate.Event;
import lombok.NoArgsConstructor;
import org.learn.eventuate.coreapi.ProductInfo;

@NoArgsConstructor
public class OrderSagaCreatedEvent implements Event {

    private String orderId;
    private ProductInfo productInfo;

    public OrderSagaCreatedEvent(String orderId, ProductInfo productInfo) {
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
