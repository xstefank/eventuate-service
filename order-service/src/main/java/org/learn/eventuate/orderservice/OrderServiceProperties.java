package org.learn.eventuate.orderservice;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("orderservice")
public class OrderServiceProperties {

    private String shipmentUrl = "";

    public String getShipmentUrl() {
        return shipmentUrl;
    }

    public void setShipmentUrl(String shipmentUrl) {
        this.shipmentUrl = shipmentUrl;
    }
}
