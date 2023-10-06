package com.nttdata.bc46customer.controller;

import com.nttdata.bc46customer.model.entity.Customer;
import com.nttdata.bc46customer.model.response.CustomerAccountResponse;
import com.nttdata.bc46customer.service.CustomerService;
import java.time.LocalDateTime;
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
@RequestMapping("/api")
public class CustomerController {

  @Autowired
  private CustomerService customerService;

  /**
   * Retrofit
   * Consultar todas las cuentas asociadas de un cliente
   **/
  @GetMapping("/findAccountsByCustomer/{idCustomer}")
  public Flux<CustomerAccountResponse> getAccountsByCustomer(@PathVariable("idCustomer") String idCustomer) {
    return customerService.getAccountsByCustomer(idCustomer);
  }

  /**
   * Crear un tipo de cliente bancario.
   **/
  @PostMapping("/save")
  public Mono<ResponseEntity<Customer>> save(@RequestBody Customer customer) {
    customer.setCreationDatetime(LocalDateTime.now());
    return customerService.save(customer)
        .map(customer1 -> new ResponseEntity<>(customer1, HttpStatus.CREATED))
        .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @GetMapping("/findAll")
  public Flux<Customer> findAll() {
    return customerService.findAll()
        .doOnNext(customer -> customer.toString());
  }

  /**
   * Consultar por id de cliente.
   **/
  @GetMapping("/findById/{id}")
  public Mono<ResponseEntity<Customer>> findById(@PathVariable("id") String id) {
    Mono<Customer> customer = customerService.findById(id);
    return customer.map(ResponseEntity::ok)
        .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  /**
   * Actualizar un tipo de cliente bancario.
   **/
  @PutMapping("/updateCustomerById/{idCustomer}")
  public Mono<ResponseEntity<Customer>> update(@RequestBody Customer bankCustomer,
                                               @PathVariable("idCustomer") String idCustomer) {
    return customerService.updateCustomer(bankCustomer, idCustomer)
        .map(ResponseEntity::ok)
        .defaultIfEmpty(ResponseEntity.badRequest().build());
  }

  /**
   * Eliminar un tipo de cliente por ID.
   **/
  @DeleteMapping("/deleteCustomerById/{idCustomer}")
  public Mono<ResponseEntity<Void>> deleteCustomer(@PathVariable("idCustomer") String idCustomer) {
    return customerService.deleteCustomer(idCustomer)
        .map(bankCustomer -> ResponseEntity.ok().<Void>build())
        .defaultIfEmpty(ResponseEntity.notFound().build());

  }

}
