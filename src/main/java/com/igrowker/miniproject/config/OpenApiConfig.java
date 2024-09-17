package com.igrowker.miniproject.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
        @Bean
        public OpenAPI api(){
                return new OpenAPI()
                        .info(new io.swagger.v3.oas.models.info.Info()
                                .title("Barilo API")
                                .version("1.0")
                                .description("Barilo API Documentation." +
                                        "To mitigate the problem that graduates have when organizing their\n" +
                                        " journey. Bariló offers a comprehensive solution that allows\n" +
                                        " students and their families organize their own trip from scratch, choosing\n" +
                                        " among a variety of transportation options, accommodations, meals,\n" +
                                        " insurance, and activities.")
                                .termsOfService("https://github.com/igrowker/i003-barilo-back")
                                .license(new License().name("Apache 2.6.0").url("http://springdoc.org"))
                        )
                        .components(
                                new Components()
                                        .addSecuritySchemes("bearerAuth", new SecurityScheme()
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme("bearer")
                                                .bearerFormat("JWT")
                                        )
                        );
        }
}
