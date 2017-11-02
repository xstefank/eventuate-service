package org.learn.eventuate.orderservice.domain.service;

import io.eventuate.AggregateRepository;
import io.eventuate.EntityWithIdAndVersion;
import org.learn.eventuate.coreapi.FailureInfo;
import org.learn.eventuate.coreapi.InvoiceInfo;
import org.learn.eventuate.coreapi.OrderSagaInfo;
import org.learn.eventuate.coreapi.ProductInfo;
import org.learn.eventuate.coreapi.ShipmentFailureInfo;
import org.learn.eventuate.coreapi.ShipmentInfo;
import org.learn.eventuate.orderservice.command.saga.OrderSagaCommand;
import org.learn.eventuate.orderservice.command.saga.ProcessInvoiceCommand;
import org.learn.eventuate.orderservice.command.saga.ProcessShipmentCommand;
import org.learn.eventuate.orderservice.command.saga.ProcessShipmentFailureCommand;
import org.learn.eventuate.orderservice.command.saga.StartOrderSagaCommand;
import org.learn.eventuate.orderservice.config.OrderServiceProperties;
import org.learn.eventuate.orderservice.domain.event.CompensateSagaEvent;
import org.learn.eventuate.orderservice.saga.OrderSagaAggregate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;

@Component
public class OrderSagaService {

    private static final Logger log = LoggerFactory.getLogger(OrderSagaService.class);

    private static final String REQUEST = "/request";
    private static final String COMPENSATION = "/compensate";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private OrderServiceProperties properties;

    @Autowired
    private AggregateRepository<OrderSagaAggregate, OrderSagaCommand> aggregateRepository;


    public CompletableFuture<EntityWithIdAndVersion<OrderSagaAggregate>> startSaga(String orderId, ProductInfo productInfo) {
        return aggregateRepository.save(new StartOrderSagaCommand(orderId, productInfo));
    }

    public CompletableFuture<EntityWithIdAndVersion<OrderSagaAggregate>> processShipment(ShipmentInfo shipmentInfo) {
        return aggregateRepository.update(shipmentInfo.getSagaId(), new ProcessShipmentCommand(shipmentInfo));
    }

    public CompletableFuture<EntityWithIdAndVersion<OrderSagaAggregate>> processInvoice(InvoiceInfo invoiceInfo) {
        return aggregateRepository.update(invoiceInfo.getSagaId(), new ProcessInvoiceCommand(invoiceInfo));
    }

    public CompletableFuture<EntityWithIdAndVersion<OrderSagaAggregate>> processShipmentFailure(FailureInfo failureInfo) {
        return aggregateRepository.update(failureInfo.getSagaId(), new ProcessShipmentFailureCommand(failureInfo.getCause()));
    }

    public void requestShipment(String sagaId, ProductInfo productInfo) {
        final String url = properties.getShipmentUrl() + REQUEST;
        log.info("posting shipment request for saga " + sagaId + " to " + url);

        OrderSagaInfo orderSagaInfo = new OrderSagaInfo(sagaId, productInfo);
        String result = restTemplate.postForObject(url, orderSagaInfo, String.class);
        log.info(result);
    }

    public void requestInvoice(String sagaId, ProductInfo productInfo) {
        final String url = properties.getInvoiceUrl() + REQUEST;
        log.info("posting invoice request for saga " + sagaId + " to " + url);

        OrderSagaInfo orderSagaInfo = new OrderSagaInfo(sagaId, productInfo);
        String result = restTemplate.postForObject(url, orderSagaInfo, String.class);
        log.info(result);
    }

    public void compensateSaga(String sagaId, CompensateSagaEvent compensationEvent) {
        final String url = properties.getShipmentUrl() + COMPENSATION;
        log.info("posting shipemnt compensation request for saga " + sagaId + " to " + url);

        ShipmentFailureInfo shipmentFailureInfo = new ShipmentFailureInfo(sagaId,
                compensationEvent.getShipmentId(), compensationEvent.getCause());
        //possibly handle compensation failure
        String result = restTemplate.postForObject(url, shipmentFailureInfo, String.class);
        log.info(result);
    }
}
