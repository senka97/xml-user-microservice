package com.team19.usermicroservice;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.amqp.core.Queue;

@SpringBootApplication
@EnableFeignClients
public class UsermicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UsermicroserviceApplication.class, args);
	}

	@Value("${host}")
	String host;

	@Value("${registrationrejectqueue}")
	String rejectQueue;

	@Value("${registrationapprovequeue}")
	String approveQueue;

	@Value("${messagequeue}")
	String messageQueue;

	@Bean
	Queue rejectQueue() {
		return new Queue(rejectQueue, true);
	}

	@Bean
	Queue approveQueue() {
		return new Queue(approveQueue, true);
	}

	@Bean
	Queue messageQueue() {
		return new Queue(messageQueue, true);
	}


	@Bean
	public ConnectionFactory connectionFactory() {
		CachingConnectionFactory connectionFactory = new CachingConnectionFactory(host);
		return connectionFactory;
	}

	@Bean
	public RabbitAdmin  rabbitAdmin() {
		RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory());
		rabbitAdmin.afterPropertiesSet();
		rabbitAdmin.declareQueue(rejectQueue());
		rabbitAdmin.declareQueue(approveQueue());
		rabbitAdmin.declareQueue(messageQueue());
		return rabbitAdmin;
	}

}
