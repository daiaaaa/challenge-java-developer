package br.com.neurotech.challenge.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Desafio Técnico — Análise de Crédito PF")
                        .description("API RESTful para cadastro de clientes e verificação de crédito automotivo.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Daiana")
                                .email("daianavieiradeandrade@gmail.com")
                        )
                );
    }
}

