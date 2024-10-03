package com.employeemanagement.util;

public class ConstantUrl {

    private ConstantUrl() {
    }

    public static final String BASE_URL_V1 = "/api/v1/";

    public static final String AUTH_BASE_URL = BASE_URL_V1 + "auth";
    
    public static final String AUTH_USER_REGISTERED = "/register";
    public static final String AUTHENTICATE =  "/authenticate";
    public static final String REFRESH_TOKEN = "/refresh-token";
    public static final String EMPLOYEE_BASE_URL = BASE_URL_V1 + "employees";
}
