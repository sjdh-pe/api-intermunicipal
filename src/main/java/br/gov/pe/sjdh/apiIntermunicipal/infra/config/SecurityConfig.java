package br.gov.pe.sjdh.apiIntermunicipal.infra.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

/**
 * Classe de configuração de segurança da aplicação.
 *
 * Esta configuração define as regras de autenticação/autorização, controle de sessões
 * e políticas de CORS (Cross-Origin Resource Sharing) para a API Intermunicipal.
 *
 * - Em ambiente de desenvolvimento ("dev" ou "local"), todas as requisições são liberadas.
 * - Em produção, endpoints sensíveis exigem autenticação via JWT (OAuth2 Resource Server).
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Lista de origens (domínios) permitidas para requisições CORS.
     * Obtida a partir da propriedade configurada no arquivo application.properties
     *
     * Exemplo:
     * app.cors.allowed-origins=https://meusite.com,https://portal.pe.gov.br
     */
    @Value("${app.cors.allowed-origins}")
    private String allowedOrigins;

    /**
     * Define o perfil da aplicação (ex: dev, local, prod).
     * Caso não seja especificado, o padrão é "dev".
     */
    @Value("${APP_PROFILE:dev}")
    private String profile;

    /**
     * Configura o filtro principal de segurança HTTP da aplicação.
     *
     * - Desativa CSRF (pois a API é stateless e usa tokens JWT)
     * - Define política de sessão como STATELESS (sem sessões armazenadas)
     * - Configura permissões de acesso baseadas no perfil ativo (dev/local x prod)
     * - Ativa o suporte a OAuth2 Resource Server com JWT
     * - Desativa login via formulário e autenticação básica
     * - Habilita configuração CORS customizada
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Desativa o CSRF, comum em APIs REST stateless
                .csrf(AbstractHttpConfigurer::disable)

                // Define política de sessão: sem criação ou uso de sessões
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // Define as regras de autorização para as requisições HTTP
                .authorizeHttpRequests(auth -> {
                    if ("dev".equals(profile) || "local".equals(profile)) {
                        // 🔓 Em modo desenvolvimento ou local, tudo é permitido
                        auth.anyRequest().permitAll();
                    } else {
                        // 🔐 Em produção, protege endpoints sensíveis
                        auth
                                .requestMatchers(
                                        "/auth/**",          // autenticação pública
                                        "/beneficiarios/**", // endpoints públicos de beneficiários
                                        "/upload/**",        // upload de arquivos
                                        "/arquivos/**",      // download/listagem de arquivos
                                        "/lookup/**",        // consultas de apoio
                                        "/public/**",        // área pública geral
                                        "/v3/api-docs/**",   // documentação OpenAPI
                                        "/swagger-ui/**",    // interface Swagger
                                        "/swagger-ui.html",
                                        "/swagger",
                                        "/actuator/health",  // endpoint de saúde do sistema
                                        "/actuator/health/**"
                                ).permitAll()
                                // Demais endpoints exigem autenticação
                                .anyRequest().authenticated();
                    }
                })

                // Configura autenticação via OAuth2 Resource Server usando JWT
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))

                // Desativa login de formulário e autenticação básica HTTP
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)

                // Ativa a configuração CORS definida abaixo
                .cors(Customizer.withDefaults());

        // Retorna a cadeia de filtros configurada
        return http.build();
    }

    /**
     * Configuração global de CORS para a aplicação.
     *
     * Permite definir as origens autorizadas a acessar a API e os métodos HTTP permitidos.
     * Isso é fundamental quando o front-end (React, Vue, Angular, etc.)
     * está hospedado em outro domínio ou porta.
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // Converte a string separada por vírgulas em uma lista de origens válidas
        List<String> origins = Arrays.asList(allowedOrigins.split(","));
        configuration.setAllowedOrigins(origins);

        // Define métodos HTTP aceitos
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));

        // Define cabeçalhos permitidos
        configuration.setAllowedHeaders(List.of("*"));

        // Permite envio de cookies/autenticações (importante para JWT + CORS)
        configuration.setAllowCredentials(true);

        // Define tempo máximo em segundos para cache de pré-verificações CORS
        configuration.setMaxAge(3600L);

        // Aplica a configuração CORS a todos os endpoints
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}