package com.nttdata.bc46customer.model;

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
public class Address {
  private String fullAddress;
  private String district;
  private String city;
  private String country;
}
