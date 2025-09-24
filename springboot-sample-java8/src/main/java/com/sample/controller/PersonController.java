package com.sample.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sample.entity.Person;
import com.sample.service.PersonService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/person")
public class PersonController {

	private final PersonService service;

	@PostMapping
	public ResponseEntity<Map<String, String>> create(@RequestBody Person person) {
		service.save(person);
		Map<String, String> map = new HashMap<>();
		map.put("message", "Successfully created...");
		return new ResponseEntity<>(map, HttpStatus.CREATED);
	}

	@GetMapping
	public List<Person> getAll() {
		return service.findAll();
	}
}
