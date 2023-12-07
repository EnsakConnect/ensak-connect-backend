package com.ensak.connect.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                title = "Ensak Connect - Open Api Definition",
                description = "A documentation for the available api endpoint, with ability to test with token, and also have a global idea about the project. Scroll down to the bottom to see the different DTO and models used by the application.",
                version = "0.1"
        ),
        security = {
                @SecurityRequirement(
                        name = "bearerAuth"
                )
        },
        servers = {
                @Server(url = "/", description = "Default Server URL"),
                @Server(url= "https://ensak-connect-backend-develop.up.railway.app", description = "Dev server")
                @Server(url= "http://localhost:8081", description = "Local server server")
        }
)
@SecurityScheme(
        name = "bearerAuth",
        description = "Insert JWT token to access protected endpoints.",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {
}
