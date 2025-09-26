package com.sample.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sample.entity.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}