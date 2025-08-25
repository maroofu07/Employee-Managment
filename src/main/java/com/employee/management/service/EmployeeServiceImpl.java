 package com.employee.management.service;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.employee.management.dto.EmployeeRequestDTO;
import com.employee.management.dto.EmployeeResponseDTO;
import com.employee.management.exception.EmployeeNotFoundException;
import com.employee.management.model.Employee;
import com.employee.management.repository.EmployeeRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository repository;

    @Value("${upload.path}")
    private String uploadPath;

    private String saveImage(MultipartFile image) throws IOException {
        if (image == null || image.isEmpty()) return null;
        String filename = UUID.randomUUID() + "_" + image.getOriginalFilename();
        Path path = Paths.get(uploadPath, filename);
        Files.copy(image.getInputStream(), path);
        return filename;
    }

    private EmployeeResponseDTO mapToDto(Employee emp) {
        return new EmployeeResponseDTO(
            emp.getId(), emp.getName(), emp.getEmail(), emp.getDepartment(), emp.getSalary(),
            emp.getImagePath() != null ? "/uploads/" + emp.getImagePath() : null);
    }

    public EmployeeResponseDTO createEmployee(EmployeeRequestDTO request, MultipartFile image) throws IOException {
        Employee emp = new Employee();
        BeanUtils.copyProperties(request, emp);
        emp.setImagePath(saveImage(image));
        return mapToDto(repository.save(emp));
    }

    public EmployeeResponseDTO getEmployeeById(Long id) {
        return repository.findById(id).map(this::mapToDto)
            .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id: " + id));
    }

    public Page<EmployeeResponseDTO> getAllEmployees(int page, int size, String department) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Employee> employees = department != null ?
            repository.findByDepartment(department, pageable) : repository.findAll(pageable);
        return employees.map(this::mapToDto);
    }

    public EmployeeResponseDTO updateEmployee(Long id, EmployeeRequestDTO request) {
        Employee emp = repository.findById(id)
            .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id: " + id));
        BeanUtils.copyProperties(request, emp, "id", "imagePath");
        return mapToDto(repository.save(emp));
    }

    public void deleteEmployee(Long id) {
        repository.deleteById(id);
    }

	
}