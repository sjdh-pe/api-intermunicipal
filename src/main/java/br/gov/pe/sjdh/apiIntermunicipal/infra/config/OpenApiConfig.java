package br.gov.pe.sjdh.apiIntermunicipal.infra.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        final String schemeName = "BearerAuth";
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes(schemeName, new SecurityScheme()
                                .name(schemeName)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")))
                .addSecurityItem(new SecurityRequirement().addList(schemeName))
                .info(new Info()
                        .title("🚌 API Intermunicipal – PE Livre Acesso")
                        .description("""
                            Documentação oficial da **API Intermunicipal**,
                             utilizada pela
                            **SECRETARIA DE JUSTIÇA, DIREITOS HUMANOS E PREVENÇÃO À VIOLÊNCIA (SJDHPV-PE)**
                            
                             para gerenciamento do programa **PE Livre Acesso Intermunicipal**.
                            
                            Todas as rotas estão documentadas com exemplos de requisição e resposta.
                            """)
                        .contact(new Contact()
                                .name("SJDH")
                                .url("https://www.sjdh.pe.gov.br")
                                .email("raul.franca@sjdh.pe.gov.br")));
    }
}
