package org.learn.eventuate.queryservice.repository;

import org.learn.eventuate.queryservice.model.Shipment;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ShipmentRepository extends MongoRepository<Shipment, String> {

    Shipment findByShipmentId(String shipmentId);
}
