package org.learn.eventuate.orderservice.domain.event;

import lombok.NoArgsConstructor;
import org.learn.eventuate.coreapi.ProductInfo;

@NoArgsConstructor
public class ShipmentRequestedEvent implements OrderSagaEvent {

    private ProductInfo productInfo;

    public ShipmentRequestedEvent(ProductInfo productInfo) {
        this.productInfo = productInfo;
    }

    public ProductInfo getProductInfo() {
        return productInfo;
    }
}
