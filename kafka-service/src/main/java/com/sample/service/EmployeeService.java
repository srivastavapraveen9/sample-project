package com.sample.service;


import java.util.List;
import java.util.Optional;

import com.sample.entity.Employee;

public interface EmployeeService {

	public List<Employee> getAllEmployees();

	public Optional<Employee> getEmployeeById(Long id);

	public void deleteEmployee(Long id);

	public Employee saveEmployee(Employee employee);

}
