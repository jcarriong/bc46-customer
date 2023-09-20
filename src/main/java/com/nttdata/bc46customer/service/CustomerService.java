package com.nttdata.bc46customer.service;

import com.nttdata.bc46customer.model.Customer;
import reactor.core.publisher.Mono;

public interface CustomerService {
    Mono<Customer> save(Customer customer);
    Mono<Customer> findById(String id);
}
