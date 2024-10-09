package com.igrowker.miniproject.config;

import com.cloudinary.Cloudinary;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;


import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "cloudinary")
@Validated
@Data
public class CloudinaryConfig {

    @NotBlank
    private String cloudName;

    @NotBlank
    private String apiKey;

    @NotBlank
    private String apiSecret;

    @Bean
    public Cloudinary cloudinary() {
        Map<String, String> properties = Map.of(
                "cloud_name", cloudName,
                "api_key", apiKey,
                "api_secret", apiSecret
        );
        return new Cloudinary(properties);
    }
}
