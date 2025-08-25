package com.maroof.employee.management;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.employee.management.model.Employee;
import com.employee.management.repository.EmployeeRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    @DisplayName("Save and retrieve Employee")
    void testSaveAndFindById() {
        Employee employee = Employee.builder()
                .name("Jane Doe")
                .email("jane@example.com")
                .department("Finance")
                .salary(50000.0)
                .imagePath("images/jane.jpg")
                .build();

        Employee saved = employeeRepository.save(employee);
        Optional<Employee> found = employeeRepository.findById(saved.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getEmail()).isEqualTo("jane@example.com");
    }
}
