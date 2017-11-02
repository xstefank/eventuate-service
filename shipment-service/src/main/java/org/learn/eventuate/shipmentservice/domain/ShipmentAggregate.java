package org.learn.eventuate.shipmentservice.domain;

import io.eventuate.Event;
import io.eventuate.EventUtil;
import io.eventuate.ReflectiveMutableCommandProcessingAggregate;
import org.learn.eventuate.coreapi.OrderSagaInfo;
import org.learn.eventuate.shipmentservice.command.CompensateShipmentCommand;
import org.learn.eventuate.shipmentservice.command.PrepareShipmentCommand;
import org.learn.eventuate.shipmentservice.command.ShipmentCommand;
import org.learn.eventuate.shipmentservice.domain.event.ComfirmCompensationEvent;
import org.learn.eventuate.shipmentservice.domain.event.ShipmentProcessedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ShipmentAggregate extends ReflectiveMutableCommandProcessingAggregate<ShipmentAggregate, ShipmentCommand> {

    private int price;

    private static final Logger log = LoggerFactory.getLogger(ShipmentAggregate.class);

    public List<Event> process(PrepareShipmentCommand command) {
        int price = generatePriceForOrder(command.getOrderSagaInfo());

        return EventUtil.events(new ShipmentProcessedEvent(command.getOrderSagaInfo(), price));
    }

    public List<Event> process(CompensateShipmentCommand command) {
        //shipment compensation
        log.info("shipment " + command.getFailureInfo().getShipmentId() + " compensated");

        return EventUtil.events(new ComfirmCompensationEvent(command.getFailureInfo().getSagaId()));
    }

    public void apply(ShipmentProcessedEvent event) {
        log.info("on ShipmentProcessedEvent");
        this.price = event.getPrice();
    }

    public void apply(ComfirmCompensationEvent event) {
    }

    private int generatePriceForOrder(OrderSagaInfo orderSagaInfo) {
        //return testing stub
        return 42;
    }
}
