package org.learn.eventuate.invoiceservice.controller;

import org.learn.eventuate.coreapi.OrderSagaInfo;
import org.learn.eventuate.coreapi.ParticipantFailureInfo;
import org.learn.eventuate.invoiceservice.domain.service.InvoiceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class InvoiceController {

    private static final Logger log = LoggerFactory.getLogger(InvoiceController.class);

    @Autowired
    private InvoiceService invoiceService;

    @RequestMapping(method = RequestMethod.POST, path = "/request")
    public String requestInvoice(@RequestBody OrderSagaInfo sagaInfo) {
        log.info("request invoice for saga - " + sagaInfo.getSagaId());
        invoiceService.prepareInvoice(sagaInfo);

        return "Invoice request is being processed";
    }

    @RequestMapping(method = RequestMethod.POST, path = "/compensate")
    public String compensateShipemnt(@RequestBody ParticipantFailureInfo failureInfo) {
        log.info("compensation request for saga - " + failureInfo.getSagaId());
        invoiceService.compensateInvoice(failureInfo);

        return "Invoice compensation request is being processed";
    }

}
