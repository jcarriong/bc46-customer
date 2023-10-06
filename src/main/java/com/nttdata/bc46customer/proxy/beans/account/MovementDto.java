package com.nttdata.bc46customer.proxy.beans.account;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;

/**
 * Ntt Data - Top Employer 2023.
 * Todos los derechos Reservados.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovementDto {
  /*private String operation; //Operaciones: Transferir dinero, Pagar servicios*/
  private String movementType; //Depósito, Retiro, Pago, Tran.Ctas.Prop, Tran.Ctas.Terc
  @JsonIgnore
  private String sourceAccount;
  @JsonIgnore
  private String targetAccount;
  @JsonIgnore
  private String moneda;
  private Float monto;
  private String creationDatetime;

}
