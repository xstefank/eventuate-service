package org.learn.eventuate.shipmentservice.domain.service;

import io.eventuate.AggregateRepository;
import org.learn.eventuate.coreapi.OrderInfo;
import org.learn.eventuate.shipmentservice.command.PrepareShipmentCommand;
import org.learn.eventuate.shipmentservice.command.ShipmentCommand;
import org.learn.eventuate.shipmentservice.domain.ShipmentAggregate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ShipmentService {

    @Autowired
    private AggregateRepository<ShipmentAggregate, ShipmentCommand> aggregateRepository;

    public String prepareShipment(OrderInfo orderInfo) {
        aggregateRepository.save(new PrepareShipmentCommand(orderInfo));
        return "Shipment request received and is being processed";
    }
}
