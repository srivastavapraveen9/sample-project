package com.sample.controller;

import com.sample.entity.Employee;
import com.sample.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static net.logstash.logback.argument.StructuredArguments.kv;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService service;

    @GetMapping
    public List<Employee> getAllEmployees() {
        log.info("Get all the employee details");
        return service.getAllEmployees();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        log.info("Get request for id: {}", id);
        return service.getEmployeeById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> createEmployee(@RequestBody Employee employee) {
        log.info("create request for employee: {}", employee);
        service.saveEmployee(employee);
        Map<String, String> map = new HashMap<>();
        map.put("message", "Successfully created...");
        log.info("Successfully created...");
        return new ResponseEntity<>(map, HttpStatus.CREATED);

    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
        log.info("update request for employeeId: {}", id, kv("empId", id));
        return service.getEmployeeById(id).map(existing -> {
            existing.setName(employee.getName());
            existing.setDepartment(employee.getDepartment());
            existing.setSalary(employee.getSalary());
            return ResponseEntity.ok(service.saveEmployee(existing));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        log.info("delete request for employeeId: {}", id);
        service.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }
}
