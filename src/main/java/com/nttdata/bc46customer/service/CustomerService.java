package com.nttdata.bc46customer.service;

import com.nttdata.bc46customer.model.entity.Customer;
import com.nttdata.bc46customer.model.response.CustomerAccountResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Ntt Data - Top Employer 2023.
 * Todos los derechos Reservados.
 */
public interface CustomerService {

  Flux<CustomerAccountResponse> getAccountsByCustomer(String idCustomer);

  Mono<Customer> save(Customer customer);

  Flux<Customer> findAll();

  Mono<Customer> findById(String id);

  Mono<Customer> updateCustomer(Customer bankCustomer, String idCustomer);

  Mono<Customer> deleteCustomer(String idCustomer);
}
