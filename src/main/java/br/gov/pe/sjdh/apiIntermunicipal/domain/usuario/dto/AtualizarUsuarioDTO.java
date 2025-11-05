package br.gov.pe.sjdh.apiIntermunicipal.domain.usuario.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * DTO de atualização parcial de Usuário.
 */
public record AtualizarUsuarioDTO(
        @Size(max = 100, message = "O nome pode ter no máximo 100 caracteres")
        String nome,

        @Pattern(regexp = "\\d{11}", message = "O CPF deve conter 11 dígitos numéricos")
        String cpf,

        @Size(min = 6, max = 255, message = "A senha deve ter entre 6 e 255 caracteres")
        String senha,

        @Email(message = "E-mail inválido")
        @Size(max = 100, message = "O e-mail pode ter no máximo 100 caracteres")
        String email,

        Boolean ativo
) {
}
