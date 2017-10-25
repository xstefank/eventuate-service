package org.learn.eventuate.orderservice.controller;

import org.learn.eventuate.coreapi.ShipmentInfo;
import org.learn.eventuate.orderservice.domain.service.OrderSagaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/management")
public class OrderManagementSagaController {

    @Autowired
    public OrderSagaService orderSagaService;


    @RequestMapping(method = RequestMethod.POST, params = "/shipment")
    public String shipmentGateway(ShipmentInfo shipmentInfo) {

        orderSagaService.processValidShipment(shipmentInfo);

        return String.format("Shipment {} recived by order-service", shipmentInfo.getSagaId());
    }

}
