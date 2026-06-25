package com.prueba.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "Prueba Técnica API",
        version = "1.0.0",
        description = "API REST — Spring Boot 3 + Java 21 + PostgreSQL"
    )
)
public class OpenApiConfig {
}
