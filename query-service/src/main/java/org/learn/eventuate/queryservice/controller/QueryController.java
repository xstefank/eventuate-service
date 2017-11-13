package org.learn.eventuate.queryservice.controller;

import org.learn.eventuate.coreapi.ProductInfo;
import org.learn.eventuate.queryservice.model.Order;
import org.learn.eventuate.queryservice.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QueryController {

    public static final Logger log = LoggerFactory.getLogger(QueryController.class);

    @Autowired
    private OrderRepository orderRepository;

    @GetMapping("/test")
    public void getTest() {
        orderRepository.deleteAll();

        log.info("trying to persist entity");
        orderRepository.save(new Order("testId", new ProductInfo("testProduct", "testComment", 100)));
        orderRepository.save(new Order("testId2", new ProductInfo("testProduct2", "testComment2", 200)));

        log.info("trying to retrieve data");
        orderRepository.findAll().forEach(System.out::println);
    }
}
