package com.nttdata.bc46customer.model.dto;

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
public class MovementConsumerDto implements Serializable {
  private String idMovement;
  private String operation; //Operaciones: Transferir dinero, Pagar servicios*/
  private String movementType; //Depósito, Retiro, Pago, Tran.Ctas.Prop, Tran.Ctas.Terc
  private String sourceAccount;
  private String targetAccount;
  private String moneda;
  private Float monto;
  private String creationDatetime;

}
