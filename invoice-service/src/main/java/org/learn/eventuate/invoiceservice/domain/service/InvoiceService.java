package org.learn.eventuate.invoiceservice.domain.service;

import io.eventuate.AggregateRepository;
import io.eventuate.EntityWithIdAndVersion;
import org.learn.eventuate.coreapi.InvoiceInfo;
import org.learn.eventuate.coreapi.OrderSagaInfo;
import org.learn.eventuate.invoiceservice.command.InvoiceCommand;
import org.learn.eventuate.invoiceservice.command.PrepareInvoiceCommand;
import org.learn.eventuate.invoiceservice.config.InvoiceServiceProperties;
import org.learn.eventuate.invoiceservice.domain.InvoiceAggregate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;

@Service
public class InvoiceService {

    private static final Logger log = LoggerFactory.getLogger(InvoiceService.class);

    private static final String INVOICE_URL_PATH = "/management/invoice";

    @Autowired
    private InvoiceServiceProperties properties;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AggregateRepository<InvoiceAggregate, InvoiceCommand> aggregateRepository;

    public CompletableFuture<EntityWithIdAndVersion<InvoiceAggregate>> prepareInvoice(OrderSagaInfo sagaInfo) {
        return aggregateRepository.save(new PrepareInvoiceCommand(sagaInfo));
    }

    public void sendInvoice(InvoiceInfo invoiceInfo) {
        String url = properties.getOrderUrl() + INVOICE_URL_PATH;

        log.info("sending invoice to " + url);
        String response = restTemplate.postForObject(url, invoiceInfo, String.class);
        log.info(response);
    }
}
