package org.learn.eventuate.shipmentservice;

import io.eventuate.AggregateRepository;
import io.eventuate.EventuateAggregateStore;
import io.eventuate.javaclient.driver.EventuateDriverConfiguration;
import io.eventuate.javaclient.spring.EnableEventHandlers;
import org.learn.eventuate.shipmentservice.command.ShipmentCommand;
import org.learn.eventuate.shipmentservice.domain.ShipmentAggregate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({EventuateDriverConfiguration.class})
@EnableAutoConfiguration
@EnableEventHandlers
public class ShipmentServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShipmentServiceApplication.class, args);
    }

    @Bean
    public AggregateRepository<ShipmentAggregate, ShipmentCommand> aggregateRepository(EventuateAggregateStore store) {
        return new AggregateRepository<>(ShipmentAggregate.class, store);
    }
}
