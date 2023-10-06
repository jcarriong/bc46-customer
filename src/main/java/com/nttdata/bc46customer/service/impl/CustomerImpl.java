package com.nttdata.bc46customer.service.impl;

import com.nttdata.bc46customer.exceptions.CustomerNotFoundException;
import com.nttdata.bc46customer.exceptions.CustomerTypeBadRequestException;
import com.nttdata.bc46customer.model.entity.Customer;
import com.nttdata.bc46customer.model.response.CustomerAccountResponse;
import com.nttdata.bc46customer.proxy.AccountRetrofitClient;
import com.nttdata.bc46customer.repository.CustomerRepository;
import com.nttdata.bc46customer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Ntt Data - Top Employer 2023.
 * Todos los derechos Reservados.
 */
@Service
public class CustomerImpl implements CustomerService {
  @Autowired
  CustomerRepository customerRepository;

  @Autowired
  AccountRetrofitClient accountRetrofitClient;

  @Override
  public Mono<Customer> findById(String id) {
    return customerRepository.findById(id)
        .switchIfEmpty(Mono.error(() -> new CustomerNotFoundException(id)));
  }

  @Override
  public Flux<Customer> findAll() {
    return customerRepository.findAll();
  }

  @Override
  public Flux<CustomerAccountResponse> getAccountsByCustomer(String idCustomer) {
    CustomerAccountResponse customerAccountResponse = new CustomerAccountResponse();

    return customerRepository.findById(idCustomer)
        .flatMapMany(customer -> {
          customerAccountResponse.setCustomer(customer);
          return Flux.just(customerAccountResponse);
        }).flatMap(accountResponse -> accountRetrofitClient.getAccountsByCustomer(idCustomer)
            .map(accountDto -> {
              accountResponse.setListBankAccountDto(accountDto);
              return accountResponse;
            }));
  }

  @Override
  public Mono<Customer> save(Customer customer) {
    return Mono.just(customer)
        .filter(customer1 -> isValidCustomerType(customer1.getCustomerType()))
        .switchIfEmpty(Mono.error(() ->
            new CustomerTypeBadRequestException(customer.getCustomerType())))
        .flatMap(customerToSave -> generateCustomId() // genera un identificador personalizado
            .flatMap(customId -> {
              customerToSave.setIdCustomer(customId);
              return customerRepository.save(customerToSave); //guarda el cliente con el customId
            }));
  }

  private Mono<String> generateCustomId() {
    return customerRepository.count() // Contar la cantidad actual de documentos
        .map(count -> {
          int nextNumber = count.intValue() + 1;
          return "C" + String.format("%04d", nextNumber);
        })
        .defaultIfEmpty("C0001"); // Si no hay documentos, comenzar desde C001
  }

  private boolean isValidCustomerType(String customerType) {
    if (customerType != null) {
      return customerType.equalsIgnoreCase("personal")
          || customerType.equalsIgnoreCase("empresarial");
    }
    return false;
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
          currentCustomer.setPhoneNumber(bankCustomer.getPhoneNumber());
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
