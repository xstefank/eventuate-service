package org.learn.eventuate.orderservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("orderservice")
public class OrderServiceProperties {

    private String shipmentUrl = "";
    private String invoiceUrl = "";

    public String getShipmentUrl() {
        return shipmentUrl;
    }

    public void setShipmentUrl(String shipmentUrl) {
        this.shipmentUrl = shipmentUrl;
    }

    public String getInvoiceUrl() {
        return invoiceUrl;
    }

    public void setInvoiceUrl(String invoiceUrl) {
        this.invoiceUrl = invoiceUrl;
    }
}
