package org.learn.eventuate.orderservice.controller;

import org.learn.eventuate.coreapi.ProductInfo;
import org.learn.eventuate.orderservice.domain.service.OrderService;
import org.learn.eventuate.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public String createOrder(@RequestBody ProductInfo productInfo) {
        String orderId = Util.generateId();

        orderService.save(orderId, productInfo);

        return "OrderAggregate posted - " + orderId;
    }

}
