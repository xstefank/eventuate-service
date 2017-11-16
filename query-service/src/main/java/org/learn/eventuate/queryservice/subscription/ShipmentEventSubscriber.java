package org.learn.eventuate.queryservice.subscription;

import io.eventuate.DispatchedEvent;
import io.eventuate.EventHandlerMethod;
import io.eventuate.EventSubscriber;
import org.learn.eventuate.coreapi.ConfirmCompensationEvent;
import org.learn.eventuate.coreapi.ShipmentProcessedEvent;
import org.learn.eventuate.queryservice.model.Shipment;
import org.learn.eventuate.queryservice.repository.ShipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@EventSubscriber(id = "shipmentEventSubscriber ")
public class ShipmentEventSubscriber {

    @Autowired
    private ShipmentRepository shipmentRepository;

    @EventHandlerMethod
    public void onShipmentProcessedEvent(DispatchedEvent<ShipmentProcessedEvent> dispatchedEvent) {
        ShipmentProcessedEvent event = dispatchedEvent.getEvent();
        shipmentRepository.save(new Shipment(event.getId(), event.getPrice()));
    }

    @EventHandlerMethod
    public void onConfirmCompensationEvent(DispatchedEvent<ConfirmCompensationEvent> dispatchedEvent) {
        ConfirmCompensationEvent event = dispatchedEvent.getEvent();

        Shipment shipment = shipmentRepository.findByShipmentId(event.getShipmentId());
        System.out.println(shipment.getShipmentId());
        shipment.setCanceled(true);
        shipmentRepository.save(shipment);
    }
}
