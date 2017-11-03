package org.learn.eventuate.shipmentservice.domain.service;

import io.eventuate.AggregateRepository;
import org.learn.eventuate.coreapi.OrderSagaInfo;
import org.learn.eventuate.coreapi.ParticipantFailureInfo;
import org.learn.eventuate.shipmentservice.command.CompensateShipmentCommand;
import org.learn.eventuate.shipmentservice.command.PrepareShipmentCommand;
import org.learn.eventuate.shipmentservice.command.ShipmentCommand;
import org.learn.eventuate.shipmentservice.domain.ShipmentAggregate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ShipmentService {

    @Autowired
    private AggregateRepository<ShipmentAggregate, ShipmentCommand> aggregateRepository;

    public String prepareShipment(OrderSagaInfo orderSagaInfo) {
        aggregateRepository.save(new PrepareShipmentCommand(orderSagaInfo));
        return "Shipment request received and is being processed";
    }

    public void compensateShipment(ParticipantFailureInfo failureInfo) {
        aggregateRepository.update(failureInfo.getId(), new CompensateShipmentCommand(failureInfo));
    }
}
