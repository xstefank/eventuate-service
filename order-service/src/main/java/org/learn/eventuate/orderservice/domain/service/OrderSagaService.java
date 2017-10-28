package org.learn.eventuate.orderservice.domain.service;

import io.eventuate.AggregateRepository;
import io.eventuate.EntityWithIdAndVersion;
import org.learn.eventuate.coreapi.ProductInfo;
import org.learn.eventuate.coreapi.ShipmentInfo;
import org.learn.eventuate.orderservice.command.OrderSagaCommand;
import org.learn.eventuate.orderservice.command.ProcessShipmentCommand;
import org.learn.eventuate.orderservice.command.StartOrderSagaCommand;
import org.learn.eventuate.orderservice.saga.OrderSagaAggregate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class OrderSagaService {

    @Autowired
    private AggregateRepository<OrderSagaAggregate, OrderSagaCommand> aggregateRepository;

    private Logger log = LoggerFactory.getLogger(OrderSagaService.class);

    public CompletableFuture<EntityWithIdAndVersion<OrderSagaAggregate>> startSaga(String orderId, ProductInfo productInfo) {
        return aggregateRepository.save(new StartOrderSagaCommand(orderId, productInfo));
    }

    public CompletableFuture<EntityWithIdAndVersion<OrderSagaAggregate>> processValidShipment(ShipmentInfo shipmentInfo) {
        return aggregateRepository.update(shipmentInfo.getSagaId(), new ProcessShipmentCommand(shipmentInfo));
    }

}
