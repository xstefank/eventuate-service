package org.learn.eventuate.invoiceservice;

import io.eventuate.AggregateRepository;
import io.eventuate.EventuateAggregateStore;
import org.learn.eventuate.invoiceservice.command.InvoiceCommand;
import org.learn.eventuate.invoiceservice.domain.InvoiceAggregate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class InvoiceServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InvoiceServiceApplication.class, args);
	}

	@Bean
	public AggregateRepository<InvoiceAggregate, InvoiceCommand> aggregateRepository(EventuateAggregateStore store) {
		return new AggregateRepository<>(InvoiceAggregate.class, store);
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}
}
