package org.learn.eventuate.orderservice;

import io.eventuate.AggregateRepository;
import io.eventuate.EventuateAggregateStore;
import io.eventuate.javaclient.driver.EventuateDriverConfiguration;
import io.eventuate.javaclient.spring.EnableEventHandlers;
import org.learn.eventuate.orderservice.command.OrderCommand;
import org.learn.eventuate.orderservice.command.OrderSagaCommand;
import org.learn.eventuate.orderservice.domain.OrderAggregate;
import org.learn.eventuate.orderservice.saga.OrderSagaAggregate;
import org.learn.eventuate.orderservice.swagger.SwaggerConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({EventuateDriverConfiguration.class, SwaggerConfiguration.class})
@EnableAutoConfiguration
@EnableEventHandlers
public class OrderServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderServiceApplication.class, args);
	}

	@Bean
	public AggregateRepository<OrderAggregate, OrderCommand> orderAggregateRepository(EventuateAggregateStore eventStore) {
		return new AggregateRepository<>(OrderAggregate.class, eventStore);
	}

	@Bean
	public AggregateRepository<OrderSagaAggregate, OrderSagaCommand> orderManagementSagaRepository(EventuateAggregateStore eventStore) {
		return new AggregateRepository<>(OrderSagaAggregate.class, eventStore);
	}

}
