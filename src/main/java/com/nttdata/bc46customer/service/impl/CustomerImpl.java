package com.nttdata.bc46customer.service.impl;

import com.nttdata.bc46customer.exceptions.CustomerNotFoundException;
import com.nttdata.bc46customer.exceptions.CustomerTypeBadRequestException;
import com.nttdata.bc46customer.model.entity.Customer;
import com.nttdata.bc46customer.model.response.CustomerAccountResponse;
import com.nttdata.bc46customer.proxy.AccountRetrofitClient;
import com.nttdata.bc46customer.repository.CustomerRepository;
import com.nttdata.bc46customer.service.CustomerService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import java.io.IOException;
import java.util.Collections;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Ntt Data - Top Employer 2023.
 * Todos los derechos Reservados.
 */
@Slf4j
@Service
public class CustomerImpl implements CustomerService {
  @Autowired
  CustomerRepository customerRepository;

  @Autowired
  AccountRetrofitClient accountRetrofitClient;

  @Autowired
  private CacheManager cacheManager;

  /** Este método utilizará la caché configurada para buscar
   * respuestas basadas en la clave proporcionada (idCustomer).
   * Si encuentra una respuesta en caché, la devolverá; de lo contrario, devolverá null.
   * Este método utilizará la caché configurada para buscar
   */
  @Cacheable(value = "customerAccountCache", key = "#idCustomer")
  public CustomerAccountResponse getCachedResponse(String idCustomer) {


    // Búsqueda en Redis utilizando cacheManager: **/
    Cache cache = cacheManager.getCache("customerAccountCache");
    if (cache != null) {
      Cache.ValueWrapper valueWrapper = cache.get(idCustomer);
      if (valueWrapper != null) {
        return (CustomerAccountResponse) valueWrapper.get();
      }
    }

    return null;
  }

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
  @CircuitBreaker(name = "myCircuitBreaker", fallbackMethod = "fallbackMethod")
  public Flux<CustomerAccountResponse> getAccountsByCustomer(String idCustomer) {
    // Flux.defer() retrasa la ejecución del código contenido en el bloque lambda hasta que
    //  alguien se suscriba al Flux.*/
    return Flux.defer(() -> {
      // Intenta obtener la respuesta desde la caché. */
      CustomerAccountResponse cachedResponse = getCachedResponse(idCustomer);
      // Si se encuentra en caché, devuelve la respuesta almacenada */
      if (cachedResponse != null) {
        return Flux.just(cachedResponse);
      } else {
        // Si no se encuentra en caché, realiza la llamada principal al servicio de cuentas. */
        CustomerAccountResponse customerAccountResponse = new CustomerAccountResponse();

        return customerRepository.findById(idCustomer)
            .flatMapMany(customer -> {
              customerAccountResponse.setCustomer(customer);
              return Flux.just(customerAccountResponse);
            })
            .flatMap(accountResponse -> accountRetrofitClient.getAccountsByCustomer(idCustomer)
                .map(accountDto -> {
                  accountResponse.setListBankAccountDto(accountDto);
                  return accountResponse;
                })
                .doOnNext(response -> {
                  /** Guarda la respuesta en caché después de obtenerla. */
                  saveResponseToCache(idCustomer, response);
                  log.info("Respuesta guardada en caché para idCustomer: {}", idCustomer);
                })
            )
            .onErrorResume(IOException.class, error -> {
              // Maneja la excepción IOException aquí y realiza acciones de fallback. */
              log.error("Error al realizar la llamada a accountRetrofitClient", error);
              error.printStackTrace(); // Agrega esta línea para imprimir la traza de la excepción. */
              //Si no se encuentra en caché, llama al fallbackMethod para obtener la respuesta alternativa*/
              return fallbackMethod(idCustomer, error);
            });
      }
    });
  }

  /** Lógica para guardar la respuesta en la caché. */
  private void saveResponseToCache(String idCustomer, CustomerAccountResponse response) {
    // Utiliza el cacheManager como método de acceso a Redis */

    // Guardado en Redis utilizando cacheManager: */
    Cache cache = cacheManager.getCache("customerAccountCache");
    if (cache != null) {
      cache.put(idCustomer, response);
    }
  }

  /** Método de fallback de CircuitBreaker. */
  public Flux<CustomerAccountResponse> fallbackMethod(String idCustomer, Throwable throwable) {
    // Agrega un registro en el método de fallback para verificar si se está ejecutando */
    log.error("FallbackMethod invocado para idCustomer: {}", idCustomer, throwable);

    // Maneja el fallback aquí, por ejemplo, devuelve una respuesta alternativa estática. */
    return Flux.just(createFallbackResponse());
  }

  private CustomerAccountResponse createFallbackResponse() {
    // Crea y devuelve una respuesta alternativa estática personalizada. */
    return CustomerAccountResponse.builder()
        .customer(Customer.builder()
            .customerType("Error en el servicio de cuentas")
            .customerCategory("Error en la categoría de cliente")
            .dni("Error en el DNI")
            .firstName("Error en el nombre")
            .lastName("Error en el apellido")
            .email("Error en el email")
            .phoneNumber("Error en el número de teléfono")
            .address(null)
            .build())
        .listBankAccountDto(Collections.emptyList())
        .build();
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
