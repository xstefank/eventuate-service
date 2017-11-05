package org.learn.eventuate.shipmentservice.domain;

import io.eventuate.DispatchedEvent;
import io.eventuate.EventHandlerMethod;
import io.eventuate.EventSubscriber;
import org.learn.eventuate.coreapi.ParticipantFailureInfo;
import org.learn.eventuate.coreapi.ShipmentInfo;
import org.learn.eventuate.shipmentservice.config.ShipmentServiceProperties;
import org.learn.eventuate.shipmentservice.domain.event.ConfirmCompensationEvent;
import org.learn.eventuate.shipmentservice.domain.event.ShipmentPreparationFailedEvent;
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
    private static final String COMPENSATION = "/compensation";
    private static final String FAILURE = "/fail";

    private static final Logger log = LoggerFactory.getLogger(ShipmentEventSubscriber.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ShipmentServiceProperties properties;

    @EventHandlerMethod
    public void onShipmentProcessedEvent(DispatchedEvent<ShipmentProcessedEvent> dispatchedEvent) {
        ShipmentProcessedEvent event = dispatchedEvent.getEvent();

        String url = properties.getOrderUrl() + SHIPMENT_PATH;

        ShipmentInfo shipmentInfo = new ShipmentInfo(event.getOrderSagaInfo().getSagaId(),
                dispatchedEvent.getEntityId(), event.getPrice());
        String response = restTemplate.postForObject(url, shipmentInfo, String.class);

        log.info(response);
    }

    @EventHandlerMethod
    public void onComfirmCompensationEvent(DispatchedEvent<ConfirmCompensationEvent> dispatchedEvent) {

        String url = properties.getOrderUrl() + SHIPMENT_PATH + COMPENSATION;

        String response = restTemplate.postForObject(url, dispatchedEvent.getEvent().getSagaId(), String.class);
        log.info(response);

    }

    @EventHandlerMethod
    public void onShipmentPreparationFailedEvent(DispatchedEvent<ShipmentPreparationFailedEvent> dispatchedEvent) {
        String url = properties.getOrderUrl() + SHIPMENT_PATH + FAILURE;

        ShipmentPreparationFailedEvent event = dispatchedEvent.getEvent();

        ParticipantFailureInfo failureInfo = new ParticipantFailureInfo(event.getSagaId(),
                dispatchedEvent.getEntityId(), event.getCause());
        String response = restTemplate.postForObject(url, failureInfo, String.class);
        log.info(response);
    }
}
