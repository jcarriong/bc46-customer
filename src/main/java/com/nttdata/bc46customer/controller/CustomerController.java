package com.nttdata.bc46customer.controller;

import com.nttdata.bc46customer.model.Customer;
import com.nttdata.bc46customer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    /**
     * Crear un tipo de cliente bancario
     **/
    @PostMapping("/save")
    public Mono<ResponseEntity<Customer>> save(@RequestBody Customer customer) {
        customer.setCreationDatetime(LocalDateTime.now());
        return customerService.save(customer)
                .map(customer1 -> new ResponseEntity<>(customer1, HttpStatus.CREATED))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Consultar por id de cliente
     **/
    @GetMapping("/findById/{id}")
    public Mono<ResponseEntity<Customer>> findById(@PathVariable("id") String id) {
        Mono<Customer> customer = customerService.findById(id);
        return customer.map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

}
