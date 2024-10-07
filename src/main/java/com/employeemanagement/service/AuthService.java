package com.employeemanagement.service;

import com.employeemanagement.model.dto.AuthenticationRequest;
import com.employeemanagement.model.dto.RegisterRequest;
import com.employeemanagement.model.entity.User;
import com.employeemanagement.model.response.AuthenticationResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface AuthService {

    AuthenticationResponse register(RegisterRequest request);

    AuthenticationResponse authenticate(AuthenticationRequest request);

    void saveUserToken(User user, String jwtToken);

    void revokeAllUserTokens(User user);

    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;
}


