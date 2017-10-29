package org.learn.eventuate.orderservice.domain.service;

import org.learn.eventuate.coreapi.OrderSagaInfo;
import org.learn.eventuate.coreapi.ProductInfo;
import org.learn.eventuate.orderservice.config.OrderServiceProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final String REQUEST = "/request";

    private static final Logger log = LoggerFactory.getLogger(ShipmentService.class);

    public ShipmentService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public void requestShipment(String sagaId, ProductInfo productInfo) {
        final String url = properties.getShipmentUrl() + REQUEST;
        log.info("posting shipment request for saga " + sagaId + " to " + url);

        OrderSagaInfo orderSagaInfo = new OrderSagaInfo(sagaId, productInfo);
        String result = restTemplate.postForObject(url, orderSagaInfo, String.class);
        log.info(result);
    }

}
