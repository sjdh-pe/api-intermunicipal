package br.gov.pe.sjdh.apiIntermunicipal.infra.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("API PE Livre Acesso Intermunicipal")
                .version("1.0")
                .description("API para gestão de beneficiários do Programa PE Livre Acesso Intermunicipal")
                .contact(new Contact()
                    .name("SJDH")
                    .url("https://www.sjdh.pe.gov.br")
                    .email("contato@sjdh.pe.gov.br")));
    }
}
