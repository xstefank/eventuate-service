package org.learn.eventuate.queryservice.repository;

import org.learn.eventuate.queryservice.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderRepository extends MongoRepository<Order, String> {

}
