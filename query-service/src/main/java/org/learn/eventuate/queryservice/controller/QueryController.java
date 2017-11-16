package org.learn.eventuate.queryservice.controller;

import org.learn.eventuate.queryservice.model.Order;
import org.learn.eventuate.queryservice.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class QueryController {

    public static final Logger log = LoggerFactory.getLogger(QueryController.class);

    @Autowired
    private OrderRepository orderRepository;

    @GetMapping("/clean")
    public void clean() {
        orderRepository.deleteAll();
    }

    @GetMapping("/orders")
    public List<Order> getTest() {
        return orderRepository.findAll();
    }
}
