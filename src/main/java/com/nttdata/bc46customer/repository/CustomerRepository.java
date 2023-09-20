package com.nttdata.bc46customer.repository;

import com.nttdata.bc46customer.model.Customer;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface CustomerRepository extends ReactiveMongoRepository<Customer, String> {

}
