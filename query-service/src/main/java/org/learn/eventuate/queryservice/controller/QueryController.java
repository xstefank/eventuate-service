package org.learn.eventuate.queryservice.controller;

import org.learn.eventuate.queryservice.model.Invoice;
import org.learn.eventuate.queryservice.model.Order;
import org.learn.eventuate.queryservice.model.Shipment;
import org.learn.eventuate.queryservice.repository.InvoiceRepository;
import org.learn.eventuate.queryservice.repository.OrderRepository;
import org.learn.eventuate.queryservice.repository.ShipmentRepository;
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

    @Autowired
    private ShipmentRepository shipmentRepository;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @GetMapping("/clean")
    public void clean() {
        orderRepository.deleteAll();
        shipmentRepository.deleteAll();
        invoiceRepository.deleteAll();
    }

    @GetMapping("/order")
    public List<Order> getOrders() {
        return orderRepository.findAll();
    }

    @GetMapping("/shipment")
    public List<Shipment> getShipments() {
        return shipmentRepository.findAll();
    }

    @GetMapping("/invoice")
    public List<Invoice> getInvoices() {
        return invoiceRepository.findAll();
    }
}
