package com.sample.service.impl;

import org.springframework.stereotype.Service;

import com.sample.entity.Employee;
import com.sample.repository.EmployeeRepository;
import com.sample.service.EmployeeService;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

	private final EmployeeRepository repository;

	public List<Employee> getAllEmployees() {
		return repository.findAll();
	}

	public Optional<Employee> getEmployeeById(Long id) {
		return repository.findById(id);
	}

	public Employee saveEmployee(Employee employee) {
		return repository.save(employee);
	}

	public void deleteEmployee(Long id) {
		repository.deleteById(id);
	}
}
