package com.employeemanagement.controller;

import com.employeemanagement.model.dto.AuthenticationRequest;
import com.employeemanagement.model.dto.RegisterRequest;
import com.employeemanagement.model.response.AuthenticationResponse;
import com.employeemanagement.service.AuthService;
import com.employeemanagement.util.ConstantUrl;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping(ConstantUrl.AUTH_BASE_URL)
@AllArgsConstructor
@Hidden
@CrossOrigin
public class AuthController {

    private final AuthService authService;

    @PostMapping(ConstantUrl.AUTH_USER_REGISTERED)
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {

        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping(ConstantUrl.AUTHENTICATE)
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {

        return ResponseEntity.ok(authService.authenticate(request));
    }

    @PostMapping(ConstantUrl.REFRESH_TOKEN)
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {

        authService.refreshToken(request, response);
    }

}
