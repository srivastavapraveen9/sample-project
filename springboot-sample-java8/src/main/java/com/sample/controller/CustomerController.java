package com.sample.controller;

import java.util.List;
import org.springframework.web.bind.annotation.*;

import com.sample.client.OrderServiceClient;
import com.sample.entity.Person;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customer")
@Slf4j
public class CustomerController {

	private final OrderServiceClient service;

	@GetMapping
	public List<Person> getAll() {
		log.info("Calling person details");
		return service.getOrderDetails();
	}
}
