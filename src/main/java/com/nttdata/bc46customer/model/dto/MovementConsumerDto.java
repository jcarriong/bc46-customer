package com.nttdata.bc46customer.proxy.beans.account;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
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
public class MovementDto implements Serializable {
  @JsonIgnore
  private String idMovement;
  private String operation; //Operaciones: Transferir dinero, Pagar servicios*/
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
