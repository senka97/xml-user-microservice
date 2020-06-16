package com.team19.usermicroservice.rabbitmq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Producer {

    /*
     * RabbitTemplate je pomocna klasa koja uproscava sinhronizovani
     * pristup RabbitMQ za slanje i primanje poruka.
     */
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /*
     * U ovom slucaju routingKey ce biti ime queue.
     * Poruka se salje u exchange (default exchange u ovom primeru) i
     * exchange ce rutirati poruke u pravi queue.
     */
    public void addRequestToQueue(String routingkey, RegistrationMessage message) {
        ObjectMapper objectMapper = new ObjectMapper();
        String messageJson = null;
        try {
            messageJson = objectMapper.writeValueAsString(message);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        this.rabbitTemplate.convertAndSend(routingkey, messageJson);
    }
}
