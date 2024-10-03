package com.employeemanagement.controller;

import com.employeemanagement.common.ConstantUrl;
import com.employeemanagement.config.jwt.JwtService;
import com.employeemanagement.entity.Role;
import com.employeemanagement.model.dto.AuthenticationRequest;
import com.employeemanagement.model.dto.RegisterRequest;
import com.employeemanagement.model.response.AuthenticationResponse;
import com.employeemanagement.repository.TokenRepository;
import com.employeemanagement.service.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private JwtService jwtService;
    @MockBean
    private TokenRepository tokenRepository;

    private RegisterRequest registerRequest;
    private AuthenticationRequest authenticationRequest;
    private AuthenticationResponse authenticationResponse;

    @BeforeEach
    void setUp() {
        registerRequest = new RegisterRequest("John", "Doe", "john.doe@example.com", "password", Role.USER);
        authenticationRequest = new AuthenticationRequest("john.doe@example.com", "password");
        authenticationResponse = new AuthenticationResponse("accessToken", "refreshToken");
    }

    @Test
    void shouldRegisterUser() throws Exception {
        System.out.println("111");
        System.out.println(registerRequest);
        when(authService.register(registerRequest)).thenReturn(authenticationResponse);

        mockMvc.perform(post(ConstantUrl.AUTH_BASE_URL.concat(ConstantUrl.AUTH_USER_REGISTERED))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void shouldAuthenticateUser() throws Exception {
        when(authService.authenticate(ArgumentMatchers.any(AuthenticationRequest.class)))
                .thenReturn(authenticationResponse);

        mockMvc.perform(post(ConstantUrl.AUTH_BASE_URL.concat(ConstantUrl.AUTHENTICATE))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(authenticationRequest)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void shouldRefreshToken() throws Exception {
        String token = "Bearer some_refresh_token";
        mockMvc.perform(post(ConstantUrl.AUTH_BASE_URL.concat(ConstantUrl.REFRESH_TOKEN))
                .header("Authorization", token))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
