package com.employeemanagement.service;

import com.employeemanagement.common.ResponseMessage;
import com.employeemanagement.entity.Employee;
import com.employeemanagement.exception.EmployeeNotFoundException;
import com.employeemanagement.mapper.EmployeeMapper;
import com.employeemanagement.model.ApiResponse;
import com.employeemanagement.model.dto.EmployeeDTO;
import com.employeemanagement.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private EmployeeMapper employeeMapper;

    @InjectMocks
    private EmployeeService employeeService;

    private Employee employee;
    private EmployeeDTO employeeDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        employee = Employee.builder().id(1L).firstName("John").lastName("Doe").email("john.doe@example.com").department("Engineering").salary(75000.0).build();

        employeeDTO = new EmployeeDTO();
        employeeDTO.setFirstName("John");
        employeeDTO.setLastName("Doe");
        employeeDTO.setEmail("john.doe@example.com");
        employeeDTO.setDepartment("Engineering");
        employeeDTO.setSalary(75000.0);
    }

    @Test
    void testCreateEmployee() {
        when(employeeMapper.toEntity(employeeDTO)).thenReturn(employee);
        when(employeeRepository.save(employee)).thenReturn(employee);
        when(employeeMapper.toDTO(employee)).thenReturn(employeeDTO);

        ApiResponse<EmployeeDTO> response = employeeService.createEmployee(employeeDTO);

        assertThat(response.getStatusCode()).isEqualTo("201");
        assertThat(response.getMessage()).isEqualTo(ResponseMessage.EMPLOYEE_CREATED_SUCCESS);
        assertThat(response.getData()).isEqualTo(employeeDTO);
    }

    @Test
    void testGetAllEmployees() {
        when(employeeRepository.findAll()).thenReturn(Collections.singletonList(employee));
        when(employeeMapper.toDTO(employee)).thenReturn(employeeDTO);

        ApiResponse<List<EmployeeDTO>> response = employeeService.getAllEmployees();

        assertThat(response.getStatusCode()).isEqualTo("200");
        assertThat(response.getMessage()).isEqualTo(ResponseMessage.GET_ALL_EMPLOYEE_MESSAGE);
        assertThat(response.getData()).containsExactly(employeeDTO);
    }

    @Test
    void testGetEmployeeById() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(employeeMapper.toDTO(employee)).thenReturn(employeeDTO);

        ApiResponse<EmployeeDTO> response = employeeService.getEmployeeById(1L);

        assertThat(response.getStatusCode()).isEqualTo("200");
        assertThat(response.getMessage()).isEqualTo(ResponseMessage.GET_EMPLOYEE_MESSAGE);
        assertThat(response.getData()).isEqualTo(employeeDTO);
    }

    @Test
    void testGetEmployeeById_NotFound() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> employeeService.getEmployeeById(1L)).isInstanceOf(EmployeeNotFoundException.class).hasMessage(ResponseMessage.EMPLOYEE_NOT_FOUND_MESSAGE);
    }

    @Test
    void testUpdateEmployee() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(employeeMapper.toDTO(employee)).thenReturn(employeeDTO);

        ApiResponse<EmployeeDTO> response = employeeService.updateEmployee(1L, employeeDTO);

        assertThat(response.getStatusCode()).isEqualTo("200");
        assertThat(response.getMessage()).isEqualTo(ResponseMessage.GET_EMPLOYEE_MESSAGE);
      //  assertThat(response.getData()).isEqualTo(employeeDTO);

        ArgumentCaptor<Employee> captor = ArgumentCaptor.forClass(Employee.class);
        verify(employeeRepository).save(captor.capture());
        assertThat(captor.getValue().getFirstName()).isEqualTo(employeeDTO.getFirstName());
    }

    @Test
    void testUpdateEmployee_NotFound() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> employeeService.updateEmployee(1L, employeeDTO)).isInstanceOf(EmployeeNotFoundException.class).hasMessage(ResponseMessage.EMPLOYEE_NOT_FOUND_MESSAGE);
    }

    @Test
    void testDeleteEmployee() {
        when(employeeRepository.existsById(1L)).thenReturn(true);

        employeeService.deleteEmployee(1L);

        verify(employeeRepository).deleteById(1L);
    }

    @Test
    void testDeleteEmployee_NotFound() {
        when(employeeRepository.existsById(1L)).thenReturn(false);

        assertThatThrownBy(() -> employeeService.deleteEmployee(1L)).isInstanceOf(EmployeeNotFoundException.class).hasMessage(ResponseMessage.EMPLOYEE_NOT_FOUND_MESSAGE);
    }
}
