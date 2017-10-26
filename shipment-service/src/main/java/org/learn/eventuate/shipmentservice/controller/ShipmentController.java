package org.learn.eventuate.shipmentservice.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class ShipmentController {

    @RequestMapping(method = RequestMethod.GET, path = "/test")
    public String requestShipment() {
        return "Shipment testing message";
    }
}
