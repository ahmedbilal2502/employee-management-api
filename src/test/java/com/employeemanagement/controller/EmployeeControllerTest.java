package com.employeemanagement.controller;

import com.employeemanagement.util.ConstantUrl;
import com.employeemanagement.util.ResponseMessage;
import com.employeemanagement.model.ApiResponse;
import com.employeemanagement.model.dto.EmployeeDTO;
import com.employeemanagement.repository.EmployeeRepository;
import com.employeemanagement.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class EmployeeControllerTest {

    @MockBean
    private EmployeeService employeeService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private EmployeeRepository employeeRepository;

    @Test
    @WithMockUser("USER")
    void shouldCreateEmployee() throws Exception {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setFirstName("John");
        employeeDTO.setLastName("Doe");
        employeeDTO.setEmail("john.doe@example.com");
        employeeDTO.setDepartment("Engineering");
        employeeDTO.setSalary(75000.00);
        mockMvc.perform(post(ConstantUrl.EMPLOYEE_BASE_URL).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(employeeDTO))).andExpect(status().isOk()).andDo(print());
    }

    @Test
    @WithMockUser("USER")
    void request_validation_Create_Employee() throws Exception {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setLastName("Doe");
        employeeDTO.setEmail("john.doe");
        employeeDTO.setDepartment("Engineering");
        employeeDTO.setSalary(75000.00);

        mockMvc.perform(post(ConstantUrl.EMPLOYEE_BASE_URL).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(employeeDTO))).andExpect(status().isBadRequest()).andDo(print());
    }

    @Test
    @WithMockUser("USER")
    void shouldGetEmployeeById() throws Exception {
        Long employeeId = 1L;
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setFirstName("John");
        employeeDTO.setLastName("Doe");
        employeeDTO.setEmail("john.doe@example.com");
        employeeDTO.setDepartment("Engineering");
        employeeDTO.setSalary(75000.00);

        ApiResponse<EmployeeDTO> apiResponse = new ApiResponse<>(String.valueOf(HttpStatus.OK.value()), ResponseMessage.GET_EMPLOYEE_MESSAGE, employeeDTO);

        when(employeeService.getEmployeeById(employeeId)).thenReturn(apiResponse);

        mockMvc.perform(get(ConstantUrl.EMPLOYEE_BASE_URL + "/" + employeeId).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(jsonPath("$.message").value(ResponseMessage.GET_EMPLOYEE_MESSAGE)).andExpect(jsonPath("$.data.firstName").value("John")).andDo(print());
    }

    @Test
    @WithMockUser("USER")
    void shouldGetAllEmployees() throws Exception {
        EmployeeDTO employee1 = new EmployeeDTO();
        employee1.setFirstName("John");
        employee1.setLastName("Doe");
        employee1.setEmail("john.doe@example.com");
        employee1.setDepartment("Engineering");
        employee1.setSalary(75000.00);

        EmployeeDTO employee2 = new EmployeeDTO();
        employee2.setFirstName("Jane");
        employee2.setLastName("Doe");
        employee2.setEmail("jane.doe@example.com");
        employee2.setDepartment("Marketing");
        employee2.setSalary(65000.00);

        ApiResponse<List<EmployeeDTO>> employeeDTOS = new ApiResponse<>(String.valueOf(HttpStatus.OK.value()), ResponseMessage.GET_ALL_EMPLOYEE_MESSAGE, Arrays.asList(employee1, employee2));
        when(employeeService.getAllEmployees()).thenReturn(employeeDTOS);

        mockMvc.perform(get(ConstantUrl.EMPLOYEE_BASE_URL).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andDo(print());
    }

    @Test
    @WithMockUser("ADMIN")
    void shouldUpdateEmployee() throws Exception {
        Long employeeId = 1L;
        EmployeeDTO updatedEmployeeDTO = new EmployeeDTO();
        updatedEmployeeDTO.setFirstName("John");
        updatedEmployeeDTO.setLastName("Doe");
        updatedEmployeeDTO.setEmail("john.doe@example.com");
        updatedEmployeeDTO.setDepartment("Engineering");
        updatedEmployeeDTO.setSalary(80000.00);
        ApiResponse<EmployeeDTO> apiResponse;
        apiResponse = new ApiResponse<>(String.valueOf(HttpStatus.OK.value()), ResponseMessage.GET_EMPLOYEE_MESSAGE, updatedEmployeeDTO);

        when(employeeService.updateEmployee(eq(employeeId), any(EmployeeDTO.class))).thenReturn(apiResponse);

        mockMvc.perform(put(ConstantUrl.EMPLOYEE_BASE_URL + "/" + employeeId).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(updatedEmployeeDTO))).andExpect(status().isOk()).andDo(print());
    }

    @Test
    @WithMockUser("USER")
    void shouldDeleteEmployee() throws Exception {
        Long employeeId = 1L;

        doNothing().when(employeeService).deleteEmployee(employeeId);

        mockMvc.perform(delete(ConstantUrl.EMPLOYEE_BASE_URL + "/" + employeeId).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent()).andDo(print());
    }
}
