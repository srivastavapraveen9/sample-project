package com.sample.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sample.entity.Person;
import com.sample.service.PersonService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
@Slf4j
public class OrderController {

	private final PersonService service;

	@GetMapping
	public ResponseEntity<List<Person>> getOrderDetails() {
		log.info("Request for Order details..");
		return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
	}
}
