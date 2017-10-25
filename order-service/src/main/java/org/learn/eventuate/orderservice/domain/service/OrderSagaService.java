package org.learn.eventuate.orderservice.domain.service;

import io.eventuate.AggregateRepository;
import io.eventuate.EntityWithIdAndVersion;
import org.learn.eventuate.coreapi.ShipmentInfo;
import org.learn.eventuate.orderservice.command.OrderSagaCommand;
import org.learn.eventuate.orderservice.command.ProcessShipmentCommand;
import org.learn.eventuate.orderservice.saga.OrderSagaAggregate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class OrderSagaService {

    @Autowired
    private AggregateRepository<OrderSagaAggregate, OrderSagaCommand> aggregateRepository;

    public CompletableFuture<EntityWithIdAndVersion<OrderSagaAggregate>> processValidShipment(ShipmentInfo shipmentInfo) {
        return aggregateRepository.save(new ProcessShipmentCommand(shipmentInfo));
    }

}
