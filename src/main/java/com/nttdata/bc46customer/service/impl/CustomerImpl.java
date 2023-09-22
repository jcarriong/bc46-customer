package com.nttdata.bc46customer.service.impl;

import com.nttdata.bc46customer.exceptions.CustomerTypeBadRequestException;
import com.nttdata.bc46customer.model.Customer;
import com.nttdata.bc46customer.repository.CustomerRepository;
import com.nttdata.bc46customer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * Ntt Data - Top Employer 2023.
 * Todos los derechos Reservados.
 */
@Service
public class CustomerImpl implements CustomerService {
  @Autowired
  CustomerRepository customerRepository;

  @Override
  public Mono<Customer> findById(String id) {
    return customerRepository.findById(id);
  }

  @Override
  public Mono<Customer> save(Customer customer) {
    return Mono.just(customer)
        .filter(customer1 -> isValidCustomerType(customer1.getCustomerType()))
        .switchIfEmpty(Mono.error(() ->
            new CustomerTypeBadRequestException(customer.getCustomerType())))
        .flatMap(customerRepository::save);
  }

  private boolean isValidCustomerType(String customerType) {
    return customerType.equalsIgnoreCase("personal")
        || customerType.equalsIgnoreCase("empresarial");
  }

  @Override
  public Mono<Customer> updateCustomer(Customer bankCustomer, String idCustomer) {

    return customerRepository.findById(idCustomer)
        .flatMap(currentCustomer -> {
          currentCustomer.setCustomerType(bankCustomer.getCustomerType());
          currentCustomer.setCustomerCategory(bankCustomer.getCustomerCategory());
          currentCustomer.setDni(bankCustomer.getDni());
          currentCustomer.setFirstName(bankCustomer.getFirstName());
          currentCustomer.setLastName(bankCustomer.getLastName());
          currentCustomer.setEmail(bankCustomer.getEmail());
          currentCustomer.setUsername(bankCustomer.getUsername());
          currentCustomer.setAddress(bankCustomer.getAddress());
          currentCustomer.setUpdateDatetime(java.time.LocalDateTime.now());
          return customerRepository.save(currentCustomer);
        });

  }

  @Override
  public Mono<Customer> deleteCustomer(String idCustomer) {
    return customerRepository.findById(idCustomer)
        .flatMap(existingCustomer -> customerRepository.delete(existingCustomer)
            .then(Mono.just(existingCustomer)));
  }
}
