package org.learn.eventuate.orderservice.controller;

import org.learn.eventuate.coreapi.ShipmentInfo;
import org.learn.eventuate.orderservice.domain.service.OrderSagaService;
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


    @RequestMapping(method = RequestMethod.POST, path = "/shipment")
    public String shipmentResponse(@RequestBody ShipmentInfo shipmentInfo) {

        LoggerFactory.getLogger("sdf").info("request shipment for saga - " + shipmentInfo.getSagaId());
        orderSagaService.processValidShipment(shipmentInfo);

        return String.format("Shipment for saga %s recived by order-service", shipmentInfo.getSagaId());
    }

}
