package org.learn.eventuate.shipmentservice.domain;

import io.eventuate.Event;
import io.eventuate.EventUtil;
import io.eventuate.ReflectiveMutableCommandProcessingAggregate;
import org.learn.eventuate.coreapi.ConfirmShipmentCompensationEvent;
import org.learn.eventuate.coreapi.OrderSagaInfo;
import org.learn.eventuate.coreapi.ShipmentPreparationFailedEvent;
import org.learn.eventuate.coreapi.ShipmentProcessedEvent;
import org.learn.eventuate.shipmentservice.command.CompensateShipmentCommand;
import org.learn.eventuate.shipmentservice.command.PrepareShipmentCommand;
import org.learn.eventuate.shipmentservice.command.ShipmentCommand;
import org.learn.eventuate.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ShipmentAggregate extends ReflectiveMutableCommandProcessingAggregate<ShipmentAggregate, ShipmentCommand> {

    private String id;
    private int price;
    private boolean deleted;

    private static final Logger log = LoggerFactory.getLogger(ShipmentAggregate.class);

    public List<Event> process(PrepareShipmentCommand command) {
        id = Util.generateId();
        int price = generatePriceForOrder(command.getOrderSagaInfo());

        OrderSagaInfo orderSagaInfo = command.getOrderSagaInfo();
        if (orderSagaInfo.getProductInfo().getProductId().equals("failShipment")) {
            return EventUtil.events(new ShipmentPreparationFailedEvent(id, orderSagaInfo.getSagaId(),
                    "this is testing shipment failure stub"));
        }

        return EventUtil.events(new ShipmentProcessedEvent(id, orderSagaInfo, price));
    }

    public List<Event> process(CompensateShipmentCommand command) {
        //shipment compensation
        log.info("shipment " + command.getFailureInfo().getId() + " compensated");

        return EventUtil.events(new ConfirmShipmentCompensationEvent(command.getFailureInfo().getSagaId(), id));
    }

    public void apply(ShipmentProcessedEvent event) {
        log.info("on ShipmentProcessedEvent");
        this.id = event.getId();
        this.price = event.getPrice();
    }

    public void apply(ShipmentPreparationFailedEvent event) {
        log.info("shipment preparation failed with cause " + event.getCause());
    }

    public void apply(ConfirmShipmentCompensationEvent event) {
        this.deleted = true;
    }

    private int generatePriceForOrder(OrderSagaInfo orderSagaInfo) {
        //return testing stub
        return 42;
    }
}
