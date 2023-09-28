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
  private static final String msg = "Datos del cliente no encontrados con el ID: ";

  public CustomerNotFoundException(String idCustomer) {
    super(msg.concat(idCustomer));
    this.idCustomer = idCustomer;
  }

}
