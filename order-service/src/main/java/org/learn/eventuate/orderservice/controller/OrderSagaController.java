package org.learn.eventuate.orderservice.controller;

import org.learn.eventuate.coreapi.FailureInfo;
import org.learn.eventuate.coreapi.InvoiceInfo;
import org.learn.eventuate.coreapi.ShipmentInfo;
import org.learn.eventuate.orderservice.domain.service.OrderSagaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/management")
public class OrderSagaController {

    @Autowired
    public OrderSagaService orderSagaService;
    private Logger log = LoggerFactory.getLogger(OrderSagaController.class);


    @RequestMapping(method = RequestMethod.POST, path = "/shipment")
    public String shipmentResponse(@RequestBody ShipmentInfo shipmentInfo) {

        log.info("received shipment for saga - " + shipmentInfo.getSagaId());
        orderSagaService.processShipment(shipmentInfo);

        return String.format("Shipment for saga %s recived by order-service", shipmentInfo.getSagaId());
    }

    @RequestMapping(method = RequestMethod.POST, path = "/shipment/fail")
    public String shipmentFailure(@RequestBody FailureInfo failureInfo) {

        log.info("received shipment failure for saga - " + failureInfo.getSagaId());
        orderSagaService.processShipmentFailure(failureInfo);

        return String.format("Shipment failure for saga %s recived by order-service", failureInfo.getSagaId());
    }

    @RequestMapping(method = RequestMethod.POST, path = "/shipment/compensated")
    public String shipmentCompensated(@RequestBody String sagaId) {
        log.info("recived shipment compensation confirmation for saga - " + sagaId);
        orderSagaService.compensateShipment(sagaId);

        return "Shipment compensation is received by order-service";
    }

    @RequestMapping(method = RequestMethod.POST, path = "/invoice")
    public String invoiceResponse(@RequestBody InvoiceInfo invoiceInfo) {

        log.info("received invoice for saga - " + invoiceInfo.getSagaId());
        orderSagaService.processInvoice(invoiceInfo);

        return String.format("Invoice for saga %s received by order-service", invoiceInfo.getSagaId());
    }
}
