package org.learn.eventuate.queryservice.subscription;

import io.eventuate.DispatchedEvent;
import io.eventuate.EventHandlerMethod;
import io.eventuate.EventSubscriber;
import org.learn.eventuate.coreapi.ConfirmShipmentCompensationEvent;
import org.learn.eventuate.coreapi.ShipmentPreparationFailedEvent;
import org.learn.eventuate.coreapi.ShipmentProcessedEvent;
import org.learn.eventuate.queryservice.model.Shipment;
import org.learn.eventuate.queryservice.repository.ShipmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@EventSubscriber(id = "shipmentQuerySubscriber ")
public class ShipmentQuerySubscriber {

    private static final Logger log = LoggerFactory.getLogger(ShipmentQuerySubscriber.class);

    @Autowired
    private ShipmentRepository shipmentRepository;

    @EventHandlerMethod
    public void onShipmentProcessedEvent(DispatchedEvent<ShipmentProcessedEvent> dispatchedEvent) {
        log.info("on ShipmentProcessedEvent");
        ShipmentProcessedEvent event = dispatchedEvent.getEvent();
        shipmentRepository.save(new Shipment(event.getId(), event.getPrice()));
    }

    @EventHandlerMethod
    public void onConfirmCompensationEvent(DispatchedEvent<ConfirmShipmentCompensationEvent> dispatchedEvent) {
        ConfirmShipmentCompensationEvent event = dispatchedEvent.getEvent();

        Shipment shipment = shipmentRepository.findByShipmentId(event.getShipmentId());
        System.out.println(shipment.getShipmentId());
        shipment.setCanceled(true);
        shipmentRepository.save(shipment);
    }

    @EventHandlerMethod
    public void onShipmentPreparationFailedEvent(DispatchedEvent<ShipmentPreparationFailedEvent> dispatchedEvent) {
        ShipmentPreparationFailedEvent event = dispatchedEvent.getEvent();
        shipmentRepository.save(new Shipment(event.getShipmentId(), -1));
    }
}
