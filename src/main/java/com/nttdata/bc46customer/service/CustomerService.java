package com.nttdata.bc46customer.service;

import com.nttdata.bc46customer.model.Customer;
import reactor.core.publisher.Mono;

/**
 * Ntt Data - Top Employer 2023.
 * Todos los derechos Reservados.
 */
public interface CustomerService {
  Mono<Customer> save(Customer customer);

  Mono<Customer> findById(String id);

  Mono<Customer> updateCustomer(Customer bankCustomer, String idCustomer);

  Mono<Customer> deleteCustomer(String idCustomer);
}
