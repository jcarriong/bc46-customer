package com.nttdata.bc46customer.repository;

import com.nttdata.bc46customer.model.Customer;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Ntt Data - Top Employer 2023.
 * Todos los derechos Reservados.
 */
@Repository
public interface CustomerRepository extends ReactiveMongoRepository<Customer, String> {

}
