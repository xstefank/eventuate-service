package org.learn.eventuate.shipmentservice.controller;

import org.learn.eventuate.coreapi.OrderInfo;
import org.learn.eventuate.shipmentservice.domain.service.ShipmentService;
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
    public String requestShipment(@RequestBody OrderInfo orderInfo) {
        shipmentService.prepareShipment(orderInfo);

        return "Shipment request is being processed";
    }
}
