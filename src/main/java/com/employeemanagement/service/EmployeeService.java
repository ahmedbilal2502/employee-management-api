package com.employeemanagement.service;

import com.employeemanagement.common.ResponseMessage;
import com.employeemanagement.entity.Employee;
import com.employeemanagement.exception.EmployeeNotFoundException;
import com.employeemanagement.mapper.EmployeeMapper;
import com.employeemanagement.model.ApiResponse;
import com.employeemanagement.model.dto.EmployeeDTO;
import com.employeemanagement.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    public ApiResponse<EmployeeDTO> createEmployee(EmployeeDTO employeeDTO) {
        Employee employee = employeeMapper.toEntity(employeeDTO);
        Employee savedEmployee = employeeRepository.save(employee);

        return new ApiResponse<>(String.valueOf(HttpStatus.CREATED.value()),
                ResponseMessage.EMPLOYEE_CREATED_SUCCESS, employeeMapper.toDTO(savedEmployee));

    }

    public ApiResponse<List<EmployeeDTO>> getAllEmployees() {
        List<EmployeeDTO> employeeDTOS = employeeRepository.findAll().stream().map(employeeMapper::toDTO).toList();
        if (employeeDTOS.isEmpty()) {
            return new ApiResponse<>(
                    String.valueOf(HttpStatus.OK.value()),
                    "No employees found.",
                    employeeDTOS
            );
        }
        return new ApiResponse<>(
                String.valueOf(HttpStatus.OK.value()),
                ResponseMessage.GET_ALL_EMPLOYEE_MESSAGE,
                employeeDTOS
        );

    }

    public  ApiResponse<EmployeeDTO> getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(
                () -> new EmployeeNotFoundException(ResponseMessage.EMPLOYEE_NOT_FOUND_MESSAGE )
        );

        return new ApiResponse<>(
                String.valueOf(HttpStatus.OK.value()),
                ResponseMessage.GET_EMPLOYEE_MESSAGE,
                employeeMapper.toDTO(employee)
        );
    }

    public ApiResponse<EmployeeDTO> updateEmployee(Long id, EmployeeDTO employeeDTO) {
        Employee existingEmployee = employeeRepository.findById(id).orElseThrow(
                () -> new EmployeeNotFoundException(ResponseMessage.EMPLOYEE_NOT_FOUND_MESSAGE)
        );

        existingEmployee.setFirstName(employeeDTO.getFirstName());
        existingEmployee.setLastName(employeeDTO.getLastName());
        existingEmployee.setEmail(employeeDTO.getEmail());
        existingEmployee.setDepartment(employeeDTO.getDepartment());
        existingEmployee.setSalary(employeeDTO.getSalary());

        Employee updatedEmployee = employeeRepository.save(existingEmployee);
        return new ApiResponse<>(
                String.valueOf(HttpStatus.OK.value()),
                ResponseMessage.GET_EMPLOYEE_MESSAGE,
                employeeMapper.toDTO(updatedEmployee)
        );
    }

    public void deleteEmployee(Long id) {
        if (!employeeRepository.existsById(id)) {
            throw new EmployeeNotFoundException(ResponseMessage.EMPLOYEE_NOT_FOUND_MESSAGE);
        }
        employeeRepository.deleteById(id);
    }


}
