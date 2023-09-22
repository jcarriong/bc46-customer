package com.nttdata.bc46customer.exceptions;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * Ntt Data - Top Employer 2023.
 * Todos los derechos Reservados.
 */
@Data
@Getter
@Setter
public class CustomerNotFoundException extends RuntimeException {
  private final String idCustomer;
  private static final String msg = "Datos del cliente no encontrados";

  public CustomerNotFoundException(String idCustomer) {
    super(msg);
    this.idCustomer = idCustomer;
  }

}
