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
public class CustomerTypeBadRequestException extends RuntimeException {
  private final String customerType;
  private static final String msg = "Tipo de cliente no válido";


  public CustomerTypeBadRequestException(String customerType) {
    super(msg);
    this.customerType = customerType;
  }

}
