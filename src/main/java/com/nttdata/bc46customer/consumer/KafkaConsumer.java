package com.nttdata.bc46customer.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nttdata.bc46customer.model.Movement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaConsumer {

  @KafkaListener(topics = "topic-movimientos-cuentas", groupId = "customer-group")
  public void consume(String movementJson) {
    /** Maneja el evento de movimiento recibido de Kafka. */

    try {
      // Deserializa el JSON a un objeto Movement
      ObjectMapper objectMapper = new ObjectMapper();
      Movement movement = objectMapper.readValue(movementJson, Movement.class);

      // Ahora puedes trabajar con el objeto Movement en el consumidor
      log.info("Consumed account movement: {}", movement);
    } catch (JsonProcessingException e) {
      log.error("Error al deserializar el mensaje JSON", e);
    }

  }

}
