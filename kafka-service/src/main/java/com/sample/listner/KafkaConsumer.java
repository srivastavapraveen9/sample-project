package com.sample.listner;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class KafkaConsumer {

	@KafkaListener(topics = "test-topic")
	public void listen(String message) {
		log.info("Message recieved from test-topic: {}", message);
		System.out.println("Received: " + message);
	}
	
	@KafkaListener(topics = "test-topic-2", groupId = "sample-group")
	public void listen2(String message) {
		log.info("Message recieved from test-topic-2: {}", message);
		System.out.println("Received: " + message);
	}

}
