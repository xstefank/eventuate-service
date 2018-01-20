package org.learn.eventuate.shipmentservice.controller;

import org.learn.eventuate.coreapi.OrderSagaInfo;
import org.learn.eventuate.coreapi.ParticipantFailureInfo;
import org.learn.eventuate.shipmentservice.domain.service.ShipmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ShipmentController {

    private static final Logger log = LoggerFactory.getLogger(ShipmentController.class);

    @Autowired
    private ShipmentService shipmentService;

    @RequestMapping(method = RequestMethod.POST, path = "/request")
    public String requestShipment(@RequestBody OrderSagaInfo orderSagaInfo) {
        log.info("request shipment for saga - " + orderSagaInfo.getSagaId());
        shipmentService.prepareShipment(orderSagaInfo);

        return "Shipment request is being processed";
    }

    @RequestMapping(method = RequestMethod.POST, path = "/compensate")
    public String compensateShipemnt(@RequestBody ParticipantFailureInfo failureInfo) {
        log.info("compensation request for saga - " + failureInfo.getSagaId());
        shipmentService.compensateShipment(failureInfo);

        return "Shipment compensation request is being processed";
    }
}
