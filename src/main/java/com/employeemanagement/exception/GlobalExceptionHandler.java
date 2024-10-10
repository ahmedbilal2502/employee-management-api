package com.employeemanagement.exception;

import com.employeemanagement.util.ResponseMessage;
import com.employeemanagement.model.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = ex.getBindingResult().getAllErrors().stream().filter(FieldError.class::isInstance).map(FieldError.class::cast).collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));

        ApiResponse apiResponse = new ApiResponse(String.valueOf(HttpStatus.BAD_REQUEST.value()), ResponseMessage.REQUEST_VALIDATION_MESSAGE, errors);
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler({EmployeeNotFoundException.class, CustomUsernameNotFoundException.class})
    public ResponseEntity<ApiResponse<String>> employeeNotFound(EmployeeNotFoundException ex) {

        ApiResponse errorResponse = new ApiResponse(String.valueOf(HttpStatus.NOT_FOUND.value()), ex.getMessage(), null);

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<String>> userAlreadyExit(UserAlreadyExistsException ex) {

        ApiResponse errorResponse = new ApiResponse(String.valueOf(HttpStatus.CONFLICT.value()), ex.getMessage(), null);

        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ApiResponse<String>> invalidCredentialsException(InvalidCredentialsException ex) {

        ApiResponse errorResponse = new ApiResponse(String.valueOf(HttpStatus.UNAUTHORIZED.value()), ex.getMessage(), null);

        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiResponse<String>> badCredentialsException(BadCredentialsException ex) {

        ApiResponse errorResponse = new ApiResponse(String.valueOf(HttpStatus.UNAUTHORIZED.value()), ResponseMessage.INVALID_USER, null);

        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<String>> handleException(Exception ex) {

        ApiResponse errorResponse = new ApiResponse<>(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), ResponseMessage.GENERAL_EXCEPTION_MESSAGE, null);
        ex.printStackTrace();
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
