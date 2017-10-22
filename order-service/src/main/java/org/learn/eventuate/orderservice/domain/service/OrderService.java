package org.learn.eventuate.orderservice.domain.service;

import io.eventuate.AggregateRepository;
import io.eventuate.EntityWithIdAndVersion;
import org.learn.eventuate.coreapi.ProductInfo;
import org.learn.eventuate.orderservice.command.FileOrderCommand;
import org.learn.eventuate.orderservice.command.OrderCommand;
import org.learn.eventuate.orderservice.domain.OrderAggregate;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class OrderService {

    @Autowired
    private final AggregateRepository<OrderAggregate, OrderCommand> aggregateRepository;

    public OrderService(AggregateRepository<OrderAggregate, OrderCommand> aggregateRepository) {
        this.aggregateRepository = aggregateRepository;
    }

    public CompletableFuture<EntityWithIdAndVersion<OrderAggregate>> save(String orderId, ProductInfo info) {
        LoggerFactory.getLogger(OrderService.class).info("sending FileOrderCommand");
        return aggregateRepository.save(new FileOrderCommand(orderId, info));
    }
}
