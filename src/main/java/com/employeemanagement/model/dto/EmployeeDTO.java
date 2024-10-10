package com.employeemanagement.model.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data

public class EmployeeDTO {

    @NotBlank(message = "First name is required.")
    private String firstName;

    @NotBlank(message = "Last name is required.")
    private String lastName;

    @Email(message = "Email should be valid.")
    @NotBlank(message = "Email is required.")
    private String email;

    @NotBlank(message = "Department name is required.")
    private String department;

    @Positive(message = "Salary must be a positive number.")
    @Min(value = 0,message = "Salary must be a positive number.")
    @Max(value = 999999,message = "Salary must be less then 999999 number.")
    private Double salary;
}
