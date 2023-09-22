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
public class CustomerBadRequestException extends RuntimeException {
  private final String dni;
  private static final String msg = "El perfil de cliente ya se encuentra registrado";

  public CustomerBadRequestException(String customerType) {
    super(msg);
    this.dni = customerType;
  }

}
