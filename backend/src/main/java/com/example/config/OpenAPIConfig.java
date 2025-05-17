package com.example.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("会议室预定 API")
                        .version("1.0.0")
                        .description("后端接口"))
                .externalDocs(new ExternalDocumentation()
                        .description("前端页面")
                        .url("/"));
    }
}
