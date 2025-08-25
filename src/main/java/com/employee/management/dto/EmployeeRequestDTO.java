package com.employee.management.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeRequestDTO {
    @NotBlank(message = "Name is important")
    private String name;

    @Email(message = "write correct email")
    @NotBlank(message = "Email is mandatory ")
    private String email;

    @NotBlank(message = "provide department")
    private String department;

    @Positive(message = "should be positive")
    private double salary;

    private String imagePath;
}
