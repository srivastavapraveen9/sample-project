package com.sample.listner;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class KafkaConsumer {

	@KafkaListener(topics = "test-topic", groupId = "sample-group")
	public void listen(String message) {
		log.info("Message recieved from kafka: {}", message);
		System.out.println("Received: " + message);
	}

}
