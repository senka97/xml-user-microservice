package com.team19.usermicroservice;

import org.springframework.amqp.core.Declarables;
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

	@Bean
	Queue rejectQueue() {
		return new Queue(rejectQueue, true);
	}

	@Bean
	Queue approveQueue() {
		return new Queue(approveQueue, true);
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
		return rabbitAdmin;
	}

//	@Bean
//	public Declarables declareQueues() {
//		Queue queue1 = new Queue(rejectQueue, false);
//		queue1.setAdminsThatShouldDeclare(rabbitAdmin());
//		Queue queue2 = new Queue(approveQueue, false);
//		queue2.setAdminsThatShouldDeclare(rabbitAdmin());
//		return new Declarables(rejectQueue(), approveQueue());
//	}

}
