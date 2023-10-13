package com.nttdata.bc46customer.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.nttdata.bc46customer.model.dto.BaseAuditDto;
import java.io.Serializable;
import lombok.*;
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
@Builder
@Document(collection = "customer")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Customer extends BaseAuditDto implements Serializable {

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
