package org.learn.eventuate.shipmentservice.domain;

import io.eventuate.DispatchedEvent;
import io.eventuate.EventHandlerMethod;
import io.eventuate.EventSubscriber;
import org.learn.eventuate.coreapi.ShipmentInfo;
import org.learn.eventuate.shipmentservice.config.ShipmentServiceProperties;
import org.learn.eventuate.shipmentservice.domain.event.ShipmentProcessedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@EventSubscriber(id = "shipmentEventSubscriber")
public class ShipmentEventSubscriber {

    private static final String SHIPMENT_PATH = "/management/shipment";

    private static final Logger log = LoggerFactory.getLogger(ShipmentEventSubscriber.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ShipmentServiceProperties properties;

    @EventHandlerMethod
    public void on(DispatchedEvent<ShipmentProcessedEvent> dispatchedEvent) {
        ShipmentProcessedEvent event = dispatchedEvent.getEvent();

        String url = properties.getOrderUrl() + SHIPMENT_PATH;

        log.info("saga id - " + event.getOrderSagaInfo().getSagaId());
        ShipmentInfo shipmentInfo = new ShipmentInfo(event.getOrderSagaInfo().getSagaId(), event.getPrice());
        String response = restTemplate.postForObject(url, shipmentInfo, String.class);

        log.info(response);
    }
}
