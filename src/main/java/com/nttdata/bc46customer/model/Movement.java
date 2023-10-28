package com.nttdata.bc46customer.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.*;

/**
 * Ntt Data - Top Employer 2023.
 * Todos los derechos Reservados.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Movement implements Serializable {
  private String idMovement;
  private String operation; //Operaciones: Transferir dinero, Pagar servicios
  private String movementType; //Depósito, Retiro, Pago, Tran.Ctas.Prop, Tran.Ctas.Terc
  private String sourceAccount;
  private String targetAccount;
  private String moneda;
  private Float monto;
  private String creationDatetime;

}
