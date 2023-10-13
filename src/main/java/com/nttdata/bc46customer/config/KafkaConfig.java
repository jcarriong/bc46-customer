package com.nttdata.bc46customer.config;

import com.nttdata.bc46customer.model.dto.MovementConsumerDto;
import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.consumer.ConsumerConfig;

import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

@Configuration
public class KafkaConfig {

  @Value("${spring.kafka.bootstrap-servers}")
  private String bootstrapServers;
  @Value("${spring.kafka.consumer.group-id}")
  private String groupId;


  @Bean
  public ConsumerFactory<String, MovementConsumerDto> consumerFactory() {
    Map<String, Object> props = new HashMap<>();
    props.put("bootstrap.servers", bootstrapServers); // Configura el servidor de Kafka
    props.put("key.deserializer", StringDeserializer.class.getName());
    props.put("value.deserializer", ErrorHandlingDeserializer.class.getName());
    props.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class.getName());
    // Restringe las clases serializadas a las necesarias.
    props.put(JsonDeserializer.TRUSTED_PACKAGES, "com.nttdata.bc46customer.proxy.beans.account"); // Reemplaza con el paquete de tus clases DTO
    props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId); //Configura el groupId

    return new DefaultKafkaConsumerFactory<>(props);
  }

  @Bean
  public ConcurrentKafkaListenerContainerFactory<String, MovementConsumerDto> kafkaListenerContainerFactory() {
    ConcurrentKafkaListenerContainerFactory<String, MovementConsumerDto> factory =
        new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(consumerFactory());
    return factory;
  }

}
