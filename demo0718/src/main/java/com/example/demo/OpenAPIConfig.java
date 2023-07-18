package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenAPIConfig {

  @Value("${bezkoder.openapi.dev-url}")
  private String devUrl;

  private SecurityScheme createAPIKeyScheme() {
    return new SecurityScheme().type(SecurityScheme.Type.HTTP)
        .bearerFormat("JWT")
        .scheme("bearer");
}

  @Bean
  public OpenAPI myOpenAPI() {

    Server devServer = new Server();
    devServer.setUrl(devUrl);
    devServer.setDescription("Server URL in Development environment");

   
    Info info = new Info()
        .title("Tutorial Management API : localhost:8093")
        .version("1.0");

    return new OpenAPI().addSecurityItem(new SecurityRequirement()

        //token button
        
        .addList("Bearer Authentication"))
        .components(new Components().addSecuritySchemes
            ("Bearer Authentication", createAPIKeyScheme()))

        .info(new Info().title("My REST API")
        .description("Some custom description of API.")
        .version("1.0").contact(new Contact().name("ray")
        .email( "ray@gmail.com").url("ray@gmail.com"))
        .license(new License().name("License of API")
        .url("API license URL")))
        .info(info).servers(List.of(devServer));
  }
}