package com.nttdata.bc46customer.proxy.beans.account;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {
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
  private List<String> bankMovements; //lista de movimientos bancarios
  private String creationDatetime;
  private String updateDatetime;

}