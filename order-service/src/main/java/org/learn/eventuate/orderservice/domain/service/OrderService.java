package org.learn.eventuate.orderservice.domain.service;

import io.eventuate.AggregateRepository;
import io.eventuate.EntityWithIdAndVersion;
import io.eventuate.SaveOptions;
import org.learn.eventuate.coreapi.ProductInfo;
import org.learn.eventuate.orderservice.command.CancelOrderCommnad;
import org.learn.eventuate.orderservice.command.FileOrderCommand;
import org.learn.eventuate.orderservice.command.OrderCommand;
import org.learn.eventuate.orderservice.command.OrderCompletedCommand;
import org.learn.eventuate.orderservice.domain.OrderAggregate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Component
public class OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private final AggregateRepository<OrderAggregate, OrderCommand> aggregateRepository;

    public OrderService(AggregateRepository<OrderAggregate, OrderCommand> aggregateRepository) {
        this.aggregateRepository = aggregateRepository;
    }

    public CompletableFuture<EntityWithIdAndVersion<OrderAggregate>> save(String orderId, ProductInfo info) {
        log.info("sending FileOrderCommand");
        return aggregateRepository.save(new FileOrderCommand(orderId, info),
                Optional.of(new SaveOptions().withId(orderId)));
    }

    public CompletableFuture<EntityWithIdAndVersion<OrderAggregate>> complete(String orderId) {
        return aggregateRepository.update(orderId, new OrderCompletedCommand());
    }

    public CompletableFuture<EntityWithIdAndVersion<OrderAggregate>> cancel(String orderId) {
        return aggregateRepository.update(orderId, new CancelOrderCommnad());
    }
}
