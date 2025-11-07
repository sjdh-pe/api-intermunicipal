package br.gov.pe.sjdh.apiIntermunicipal.domain.usuario.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * DTO de entrada para cadastro de Usuário.
 */
public record DadosCadastrarUsuarioDTO(
        @NotBlank(message = "{NotBlank}")
        @Size(max = 100, message = "{Size.usuario.nome}")
        String nome,

        @NotBlank(message = "{NotBlank}")
        @Pattern(regexp = "\\d{11}", message = "{cpf.invalido}")
        String cpf,

        @NotBlank(message = "{NotBlank}")
        @Size(min = 6, max = 255, message = "{Size.usuario.senha}")
        String senha,

        @NotBlank(message = "{NotBlank}")
        @Email(message = "{Email}")
        @Size(max = 100, message = "{Size.usuario.email}")
        String email,

        Boolean ativo
) {
}
