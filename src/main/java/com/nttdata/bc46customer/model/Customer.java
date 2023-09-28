package com.nttdata.bc46customer.model;

import com.nttdata.bc46customer.model.dto.BaseAuditDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Ntt Data - Top Employer 2023.
 * Todos los derechos Reservados.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "customer")
public class Customer extends BaseAuditDto {

  @Id
  private String idCustomer;
  private String customerType;
  private String customerCategory;
  private String dni;
  private String firstName;
  private String lastName;
  private String email;
  private String phoneNumber;
  /*private String username;*/
  private Address address;

}
