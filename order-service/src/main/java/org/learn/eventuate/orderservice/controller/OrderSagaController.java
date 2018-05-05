package org.learn.eventuate.orderservice.controller;

import org.learn.eventuate.coreapi.InvoiceInfo;
import org.learn.eventuate.coreapi.ParticipantFailureInfo;
import org.learn.eventuate.coreapi.ShipmentInfo;
import org.learn.eventuate.orderservice.domain.service.OrderSagaService;
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

    @RequestMapping(method = RequestMethod.POST, path = "/shipment")
    public String shipmentResponse(@RequestBody ShipmentInfo shipmentInfo) {
        orderSagaService.processShipment(shipmentInfo);
        return String.format("Shipment for saga %s recived by order-service", shipmentInfo.getSagaId());
    }

    @RequestMapping(method = RequestMethod.POST, path = "/shipment/fail")
    public String shipmentFailure(@RequestBody ParticipantFailureInfo failureInfo) {
        orderSagaService.processShipmentFailure(failureInfo);
        return String.format("Shipment failure for saga %s received by order-service", failureInfo.getSagaId());
    }

    @RequestMapping(method = RequestMethod.POST, path = "/shipment/compensation")
    public String shipmentCompensated(@RequestBody String sagaId) {
        orderSagaService.notifyShipmentCompensated(sagaId);
        return "Shipment compensation is received by order-service";
    }

    @RequestMapping(method = RequestMethod.POST, path = "/invoice")
    public String invoiceResponse(@RequestBody InvoiceInfo invoiceInfo) {
        orderSagaService.processInvoice(invoiceInfo);
        return String.format("Invoice for saga %s received by order-service", invoiceInfo.getSagaId());
    }

    @RequestMapping(method = RequestMethod.POST, path = "/invoice/fail")
    public String invoiceFailure(@RequestBody ParticipantFailureInfo failureInfo) {
        orderSagaService.processInvoiceFailure(failureInfo);
        return String.format("Invoice failure for saga %s received by order-service", failureInfo.getSagaId());
    }

    @RequestMapping(method = RequestMethod.POST, path = "/invoice/compensation")
    public String invoiceCompensated(@RequestBody String sagaId) {
        orderSagaService.notifyInvoiceCompensated(sagaId);
        return "Invoice compensation is received by order-service";
    }
}
