package org.learn.eventuate.shipmentservice.controller;

import org.learn.eventuate.coreapi.OrderSagaInfo;
import org.learn.eventuate.shipmentservice.domain.service.ShipmentService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class ShipmentController {

    @Autowired
    private ShipmentService shipmentService;

    @RequestMapping(method = RequestMethod.POST, path = "/request")
    public String requestShipment(@RequestBody OrderSagaInfo orderSagaInfo) {
        LoggerFactory.getLogger(ShipmentController.class).info("request shipment for saga - " + orderSagaInfo.getSagaId());
        shipmentService.prepareShipment(orderSagaInfo);

        return "Shipment request is being processed";
    }
}
