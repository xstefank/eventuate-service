package org.learn.eventuate.invoiceservice.domain.service;

import io.eventuate.AggregateRepository;
import io.eventuate.EntityWithIdAndVersion;
import org.learn.eventuate.coreapi.OrderSagaInfo;
import org.learn.eventuate.invoiceservice.command.InvoiceCommand;
import org.learn.eventuate.invoiceservice.command.PrepareInvoiceCommand;
import org.learn.eventuate.invoiceservice.domain.InvoiceAggregate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class InvoiceService {

    @Autowired
    private AggregateRepository<InvoiceAggregate, InvoiceCommand> aggregateRepository;

    public CompletableFuture<EntityWithIdAndVersion<InvoiceAggregate>> prepareInvoice(OrderSagaInfo sagaInfo) {
        return aggregateRepository.save(new PrepareInvoiceCommand(sagaInfo));
    }
}
