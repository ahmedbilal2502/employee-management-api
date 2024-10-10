package com.employeemanagement.config.cors;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.List;

@Component
public class CustomCorsConfiguration implements CorsConfigurationSource {
    @Override
    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowedOriginPatterns(List.of("https://*","http://*"));

        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));

        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        return config;
    }

}
