package com.employeemanagement.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class ApiResponse<T> implements Serializable {
    private String statusCode;
    private String message;
    private transient T data;
}
