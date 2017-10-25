package org.learn.eventuate.orderservice.domain.service;

import org.learn.eventuate.coreapi.ProductInfo;
import org.learn.eventuate.orderservice.config.OrderServiceProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Service for calling REST shipment services
 */
@Component
public class ShipmentService {

    private final RestTemplate restTemplate;

    @Autowired
    private OrderServiceProperties properties;

    public ShipmentService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public void requestShipment(String sagaId, ProductInfo productInfo) {
        final String url = properties.getShipmentUrl();
        System.out.println(url);

        String result = restTemplate.getForObject(url, String.class);
        System.out.println(result);
    }

}
