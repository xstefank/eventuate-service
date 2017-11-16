package org.learn.eventuate.queryservice.repository;

import org.learn.eventuate.queryservice.model.Invoice;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface InvoiceRepository extends MongoRepository<Invoice, String> {

    Invoice findByInvoiceId(String invoiceId);

}
