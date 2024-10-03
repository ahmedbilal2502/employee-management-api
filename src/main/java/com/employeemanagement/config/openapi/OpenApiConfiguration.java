package com.employeemanagement.config.openapi;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Ahmed Bilal",
                        email = "ahmedbilal2502@gmail.com",
                        url = "https://www.google.com/"
                ),
                description = "OpenApi Documentation for spring boot project",
                title = "OpenApi specification - Ahmed Bilal",
                version = "1.0",
                license = @License(
                        name = "License name",
                        url = "url"
                ),
                termsOfService = "Term Of Service"
        ),
        servers = {
                @Server(
                        description = "Local Env",
                        url = "http://localhost:8080/"
                ),
                @Server(
                        description = "Stg Env",
                        url = "http://localhost:8080/"
                )
        }
)
public class OpenApiConfiguration {
}
