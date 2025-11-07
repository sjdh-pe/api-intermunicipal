package br.gov.pe.sjdh.apiIntermunicipal.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequestMapping(path = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name = "Autenticação", description = "Endpoints de autenticação JWT")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtEncoder jwtEncoder;

    @Value("${app.security.jwt.expiration-seconds:3600}")
    private long expirationSeconds;

    @PostMapping(path = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Login com usuário/senha", description = "Retorna um token JWT Bearer para uso nos demais endpoints.")
    @ApiResponse(responseCode = "200", description = "Autenticado",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = TokenResponse.class)))
    @ApiResponse(responseCode = "401", description = "Credenciais inválidas")
    public ResponseEntity<TokenResponse> login(@RequestBody @Valid LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        UserDetails principal = (UserDetails) authentication.getPrincipal();

        Instant now = Instant.now();
        Instant exp = now.plusSeconds(expirationSeconds);

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .subject(principal.getUsername())
                .issuedAt(now)
                .expiresAt(exp)
                .claim("scope", String.join(" ", principal.getAuthorities().stream().map(a -> a.getAuthority()).toList()))
                .build();

        JwsHeader header = JwsHeader.with(MacAlgorithm.HS256).build();
        String token = jwtEncoder.encode(JwtEncoderParameters.from(header, claims)).getTokenValue();

        TokenResponse body = new TokenResponse("Bearer", token, expirationSeconds);
        return ResponseEntity.ok(body);
    }

    @Data
    public static class LoginRequest {
        @NotBlank
        private String username; // email ou cpf
        @NotBlank
        private String password;
    }

    @Data
    public static class TokenResponse {
        private final String tokenType;
        private final String accessToken;
        private final long expiresIn;
    }
}
