package org.learn.eventuate.orderservice.domain.event;

import lombok.NoArgsConstructor;
import org.learn.eventuate.coreapi.ProductInfo;

@NoArgsConstructor
public class InvoiceRequestedEvent implements OrderSagaEvent {
    private ProductInfo productInfo;

    public InvoiceRequestedEvent(ProductInfo productInfo) {
        this.productInfo = productInfo;
    }

    public ProductInfo getProductInfo() {
        return productInfo;
    }
}
