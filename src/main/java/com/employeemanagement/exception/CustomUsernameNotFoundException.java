package com.employeemanagement.exception;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUsernameNotFoundException extends UsernameNotFoundException {
    public CustomUsernameNotFoundException(String msg) {
        super(msg);
    }
}
