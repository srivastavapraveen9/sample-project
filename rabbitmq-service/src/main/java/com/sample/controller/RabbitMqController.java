package com.sample.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sample.producer.OrderProducer;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/rabbitmq")
public class RabbitMqController {
	
	private final OrderProducer orderProducer;

	@GetMapping
	public ResponseEntity<String> getEmployeeById(@RequestParam String message) {
		orderProducer.sendOrder(message);
		return new ResponseEntity<>("Message send to rabbitMq", HttpStatus.OK);
	}
}
