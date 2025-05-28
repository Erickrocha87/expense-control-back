package com.rocha.expenditurecontrol.infra.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI getOpenAPI() {
        Contact contact = new Contact();
        contact.email("ericksaggi31@gmail.com");
        contact.url("https://github.com/Erickrocha87");

        Info info = new Info();
        info.title("Expense Control API");
        info.version("v1.0");
        info.description("This API is for managing personal expenses");
        info.contact(contact);

        return new OpenAPI().info(info);
    }
}
