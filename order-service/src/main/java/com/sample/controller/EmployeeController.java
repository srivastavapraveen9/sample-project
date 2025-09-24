package com.sample.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import com.sample.entity.Employee;
import com.sample.service.EmployeeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/employees")
public class EmployeeController {

	private final EmployeeService service;
	
	private KafkaTemplate<String, String> kafkaTemplate;

	@GetMapping
	public List<Employee> getAllEmployees() {
		return service.getAllEmployees();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
		return service.getEmployeeById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	@PostMapping
	public ResponseEntity<Map<String, String>> createEmployee(@RequestBody Employee employee) {
		service.saveEmployee(employee);
		Map<String, String> map = new HashMap<>();
		map.put("message", "Successfully created...");
		return new ResponseEntity<>(map, HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
		return service.getEmployeeById(id).map(existing -> {
			existing.setName(employee.getName());
			existing.setDepartment(employee.getDepartment());
			existing.setSalary(employee.getSalary());
			return ResponseEntity.ok(service.saveEmployee(existing));
		}).orElse(ResponseEntity.notFound().build());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
		service.deleteEmployee(id);
		return ResponseEntity.noContent().build();
	}
}
