package com.nttdata.bc46customer.proxy.beans.account;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

/**
 * Ntt Data - Top Employer 2023.
 * Todos los derechos Reservados.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountDto implements Serializable {
  @Id
  private String idAccount;
  private String accountType;
  private String idProduct;
  private String idCustomer;
  private String accountNumber; //numero de cuenta (14 digits)
  private String cci; //numero de cuenta interbancaria (20 digits)
  private Float availableBalance; //saldo disponible
  private List<PersonaDto> holderAccount; //cuenta titular 1.*
  private List<PersonaDto> authorizedSigner; //firmante autorizado 0.*
  private List<MovementDto> bankMovements; //lista de movimientos bancarios
  @JsonIgnore
  private String creationDatetime;
  @JsonIgnore
  private String updateDatetime;

}