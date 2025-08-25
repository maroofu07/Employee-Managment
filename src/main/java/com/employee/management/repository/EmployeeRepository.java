package com.employee.management.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable; // ✅ Correct import
import org.springframework.data.jpa.repository.JpaRepository;

import com.employee.management.model.Employee;
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Page<Employee> findByDepartment(String department, Pageable pageable);
}
 