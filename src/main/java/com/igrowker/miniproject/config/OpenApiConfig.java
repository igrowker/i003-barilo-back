package com.igrowker.miniproject.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.beans.factory.annotation.Configurable;

@Configurable
@OpenAPIDefinition(
        info = @Info(
                title = "Barilo API",
                version = "1.0",
                description = "Barilo API Documentation"
        )
)
public class OpenApiConfig {
}
