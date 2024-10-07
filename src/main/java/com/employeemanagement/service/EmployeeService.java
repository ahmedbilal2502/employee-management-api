package com.employeemanagement.service;

import com.employeemanagement.model.ApiResponse;
import com.employeemanagement.model.dto.EmployeeDTO;

import java.util.List;


public interface EmployeeService {


    ApiResponse<EmployeeDTO> createEmployee(EmployeeDTO employeeDTO);

    ApiResponse<List<EmployeeDTO>> getAllEmployees();


    ApiResponse<EmployeeDTO> getEmployeeById(Long id);

    ApiResponse<EmployeeDTO> updateEmployee(Long id, EmployeeDTO employeeDTO);

    void deleteEmployee(Long id);
}
