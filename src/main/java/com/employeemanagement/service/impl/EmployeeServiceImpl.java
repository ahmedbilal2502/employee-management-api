package com.employeemanagement.service.impl;

import com.employeemanagement.exception.EmployeeNotFoundException;
import com.employeemanagement.mapper.EmployeeMapper;
import com.employeemanagement.model.ApiResponse;
import com.employeemanagement.model.dto.EmployeeDTO;
import com.employeemanagement.model.entity.Employee;
import com.employeemanagement.repository.EmployeeRepository;
import com.employeemanagement.service.EmployeeService;
import com.employeemanagement.util.ResponseMessage;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    @Override
    public ApiResponse<EmployeeDTO> createEmployee(EmployeeDTO employeeDTO) {
        Employee employee = employeeMapper.toEntity(employeeDTO);
        Employee savedEmployee = employeeRepository.save(employee);

        return new ApiResponse<>(String.valueOf(HttpStatus.CREATED.value()), ResponseMessage.EMPLOYEE_CREATED_SUCCESS, employeeMapper.toDTO(savedEmployee));

    }

    @Override
    public ApiResponse<List<EmployeeDTO>> getAllEmployees() {
        List<EmployeeDTO> employeeDTOS = employeeRepository.findAll().stream().map(employeeMapper::toDTO).collect(Collectors.toList());

        if (employeeDTOS.isEmpty()) {
            return new ApiResponse<>(String.valueOf(HttpStatus.OK.value()), "No employees found.", employeeDTOS);
        }
        return new ApiResponse<>(String.valueOf(HttpStatus.OK.value()), ResponseMessage.GET_ALL_EMPLOYEE_MESSAGE, employeeDTOS);

    }

    @Override
    public ApiResponse<EmployeeDTO> getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(ResponseMessage.EMPLOYEE_NOT_FOUND_MESSAGE));

        return new ApiResponse<>(String.valueOf(HttpStatus.OK.value()), ResponseMessage.GET_EMPLOYEE_MESSAGE, employeeMapper.toDTO(employee));
    }

    @Override
    public ApiResponse<EmployeeDTO> updateEmployee(Long id, EmployeeDTO employeeDTO) {
        Employee existingEmployee = employeeRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(ResponseMessage.EMPLOYEE_NOT_FOUND_MESSAGE));

        existingEmployee.setFirstName(employeeDTO.getFirstName());
        existingEmployee.setLastName(employeeDTO.getLastName());
        existingEmployee.setEmail(employeeDTO.getEmail());
        existingEmployee.setDepartment(employeeDTO.getDepartment());
        existingEmployee.setSalary(employeeDTO.getSalary());

        Employee updatedEmployee = employeeRepository.save(existingEmployee);
        return new ApiResponse<>(String.valueOf(HttpStatus.OK.value()), ResponseMessage.GET_EMPLOYEE_MESSAGE, employeeMapper.toDTO(updatedEmployee));
    }

    @Override
    public void deleteEmployee(Long id) {
        if (!employeeRepository.existsById(id)) {
            throw new EmployeeNotFoundException(ResponseMessage.EMPLOYEE_NOT_FOUND_MESSAGE);
        }
        employeeRepository.deleteById(id);
    }


}
