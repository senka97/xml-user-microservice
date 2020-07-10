package com.team19.usermicroservice.rabbitmq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;

@Component
public class Producer {

    Logger logger = LoggerFactory.getLogger(Producer.class);

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
    public void addRequestToRegistrationQueue(String routingkey, RegistrationMessage message) {
        ObjectMapper objectMapper = new ObjectMapper();
        String messageJson = null;
        try {
            logger.info("RM-SMTQ"); //SMTQ sending message to queue, RM registration message
            messageJson = objectMapper.writeValueAsString(message);
        } catch (JsonProcessingException e) {
            logger.error("RM-SMTQ-failed");
            e.printStackTrace();
        }
        this.rabbitTemplate.convertAndSend(routingkey, messageJson);
    }

    public void addRequestToMessageQueue(String routingkey, Message message) {
        ObjectMapper objectMapper = new ObjectMapper();
        String messageJson = null;
        try {
            logger.info("M-SMTQ"); //M message, SMTQ sending message to queue
            messageJson = objectMapper.writeValueAsString(message);
        } catch (JsonProcessingException e) {
            logger.error("M-SMTQ-failed");
            e.printStackTrace();
        }
        this.rabbitTemplate.convertAndSend(routingkey, messageJson);
    }
}
