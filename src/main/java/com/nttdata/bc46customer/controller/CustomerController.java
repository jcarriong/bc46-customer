package com.nttdata.bc46customer.controller;

import com.nttdata.bc46customer.model.entity.Customer;
import com.nttdata.bc46customer.model.response.CustomerAccountResponse;
import com.nttdata.bc46customer.service.CustomerService;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Ntt Data - Top Employer 2023.
 * Todos los derechos Reservados.
 */
@RestController
@RequestMapping("/api/customers")
@Slf4j
public class CustomerController {

  @Autowired
  private CustomerService customerService;

  /**
   * Retrofit:
   * Consultar todas las cuentas asociadas de un cliente.
   **/
  int cantidadReintentos = 1;

  @GetMapping("/findAccounts/{idCustomer}")
  public Flux<CustomerAccountResponse> getAccountsByCustomer(@PathVariable("idCustomer")
                                                             String idCustomer) {
    log.info("Cantidad reintentos : {}", cantidadReintentos);
    cantidadReintentos++;
    return customerService.getAccountsByCustomer(idCustomer);
  }

  /**
   * Crear un tipo de cliente bancario.
   **/
  @PostMapping("/save")
  public Mono<ResponseEntity<Customer>> save(@RequestBody Customer customer) {
    customer.setCreationDatetime(LocalDateTime.now());
    log.info("A bank customer was created");
    return customerService.save(customer)
        .map(customer1 -> new ResponseEntity<>(customer1, HttpStatus.CREATED))
        .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @GetMapping("/findAll")
  public Flux<Customer> findAll() {
    log.info("All bank customers were consulted");
    return customerService.findAll()
        .doOnNext(customer -> customer.toString());
  }

  /**
   * Consultar por id de cliente.
   **/
  @GetMapping("/findById/{id}")
  public Mono<ResponseEntity<Customer>> findById(@PathVariable("id") String id) {
    Mono<Customer> customer = customerService.findById(id);
    log.info("Bank customer consulted by id " + id);
    return customer.map(ResponseEntity::ok)
        .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  /**
   * Actualizar un tipo de cliente bancario.
   **/
  @PutMapping("/updateCustomerById/{idCustomer}")
  public Mono<ResponseEntity<Customer>> update(@RequestBody Customer bankCustomer,
                                               @PathVariable("idCustomer") String idCustomer) {
    log.info("A bank customer was changed");
    return customerService.updateCustomer(bankCustomer, idCustomer)
        .map(ResponseEntity::ok)
        .defaultIfEmpty(ResponseEntity.badRequest().build());
  }

  /**
   * Eliminar un tipo de cliente por ID.
   **/
  @DeleteMapping("/deleteCustomerById/{idCustomer}")
  public Mono<ResponseEntity<Void>> deleteCustomer(@PathVariable("idCustomer") String idCustomer) {
    log.info("Bank customer was deleted!");
    return customerService.deleteCustomer(idCustomer)
        .map(bankCustomer -> ResponseEntity.ok().<Void>build())
        .defaultIfEmpty(ResponseEntity.notFound().build());

  }

}
