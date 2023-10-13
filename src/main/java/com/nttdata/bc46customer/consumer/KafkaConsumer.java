package com.nttdata.bc46customer.consumer;

import com.nttdata.bc46customer.model.dto.MovementConsumerDto;
import com.nttdata.bc46customer.model.Movement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaConsumer {

  @KafkaListener(topics = "topic-movimientos-cuentas", groupId = "customer-group")
  public void consume(Movement movement) {
    /** Maneja el evento de movimiento recibido de Kafka. */
    log.info("Consuming Message with Id: {}", movement.getIdMovement());

    // Realiza la transformación de Movement a MovementConsumerDto
    MovementConsumerDto movementDto = new MovementConsumerDto();
    BeanUtils.copyProperties(movement, movementDto);

    log.info("Consumed Movement: {}", movementDto);

  }

}
