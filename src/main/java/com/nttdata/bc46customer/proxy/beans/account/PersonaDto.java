package com.nttdata.bc46customer.proxy.beans.account;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Ntt Data - Top Employer 2023.
 * Todos los derechos Reservados.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PersonaDto {

  private String nombre; //nombre del titular o firmante autorizado.*
  private String dni; //numero de documento
  private String rol; //rol de la persona

}
