package com.sample.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/kafka")
@Slf4j
public class KafkaController {

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	@GetMapping
	public ResponseEntity<String> sendKafkaMessage(@RequestParam String message) {
		log.info("Message recieved for kafka: {}", message);
//		kafkaTemplate.send("test-topic","order-123", message);
		ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send("test-topic", message);

        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onSuccess(SendResult<String, String> result) {
                System.out.println("Message sent to partition: " + result.getRecordMetadata().partition() +
                                   ", offset: " + result.getRecordMetadata().offset());
            }

            @Override
            public void onFailure(Throwable ex) {
                System.out.println("Failed to send message: " + ex.getMessage());
            }
        });
		return new ResponseEntity<>("Message send to kafka", HttpStatus.OK);
	}

}
