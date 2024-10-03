package com.employeemanagement.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
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

    private String department;

    @Positive(message = "Salary must be a positive number.")
    private Double salary;
}
