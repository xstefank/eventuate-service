package org.learn.eventuate.invoiceservice.domain.service;

import io.eventuate.AggregateRepository;
import io.eventuate.EntityWithIdAndVersion;
import org.learn.eventuate.coreapi.FailureInfo;
import org.learn.eventuate.coreapi.InvoiceInfo;
import org.learn.eventuate.coreapi.OrderSagaInfo;
import org.learn.eventuate.coreapi.ParticipantFailureInfo;
import org.learn.eventuate.invoiceservice.command.CompensateInvoiceCommand;
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
    private static final String COMPENSATION = "/compensation";
    private static final String FAILURE = "/fail";

    @Autowired
    private InvoiceServiceProperties properties;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AggregateRepository<InvoiceAggregate, InvoiceCommand> aggregateRepository;

    public CompletableFuture<EntityWithIdAndVersion<InvoiceAggregate>> prepareInvoice(OrderSagaInfo sagaInfo) {
        return aggregateRepository.save(new PrepareInvoiceCommand(sagaInfo));
    }

    public CompletableFuture<EntityWithIdAndVersion<InvoiceAggregate>> compensateInvoice(ParticipantFailureInfo failureInfo) {
        return aggregateRepository.update(failureInfo.getId(), new CompensateInvoiceCommand(failureInfo));
    }

    public void sendInvoice(InvoiceInfo invoiceInfo) {
        String url = properties.getOrderUrl() + INVOICE_URL_PATH;

        log.info("sending invoice to " + url);
        String response = restTemplate.postForObject(url, invoiceInfo, String.class);
        log.info(response);
    }

    public void confirmCompensation(String sagaId) {
        String url = properties.getOrderUrl() + INVOICE_URL_PATH + COMPENSATION;

        log.info("sending invoice compensation to " + url);
        String response = restTemplate.postForObject(url, sagaId, String.class);
        log.info(response);
    }

    public void failInvoicePreparation(String sagaId, String cause) {
        String url = properties.getOrderUrl() + INVOICE_URL_PATH + FAILURE;

        log.info("sending invoice failure to " + url);

        FailureInfo failureInfo = new FailureInfo(sagaId, cause);
        String response = restTemplate.postForObject(url, failureInfo, String.class);
        log.info(response);
    }
}
