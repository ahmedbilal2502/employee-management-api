package com.employeemanagement.controller;

import com.employeemanagement.common.ConstantUrl;
import com.employeemanagement.model.ApiResponse;
import com.employeemanagement.model.dto.EmployeeDTO;
import com.employeemanagement.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ConstantUrl.EMPLOYEE_BASE_URL)
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<ApiResponse<EmployeeDTO>> createEmployee(@Valid @RequestBody EmployeeDTO employeeDTO) {

        return ResponseEntity.ok(employeeService.createEmployee(employeeDTO));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<EmployeeDTO>>> getAllEmployees() {

        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<EmployeeDTO>> getEmployeeById(@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<EmployeeDTO>> updateEmployee(@PathVariable Long id, @Valid @RequestBody EmployeeDTO employeeDTO) {

        return ResponseEntity.ok(employeeService.updateEmployee(id, employeeDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }

}
