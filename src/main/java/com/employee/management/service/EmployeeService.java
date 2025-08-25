package com.employee.management.service;

import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.employee.management.dto.EmployeeRequestDTO;
import com.employee.management.dto.EmployeeResponseDTO;

public interface EmployeeService {
    EmployeeResponseDTO createEmployee(EmployeeRequestDTO request, MultipartFile image) throws IOException;
    EmployeeResponseDTO getEmployeeById(Long id);
    Page<EmployeeResponseDTO> getAllEmployees(int page, int size, String department);
    EmployeeResponseDTO updateEmployee(Long id, EmployeeRequestDTO request);
    void deleteEmployee(Long id);
}
