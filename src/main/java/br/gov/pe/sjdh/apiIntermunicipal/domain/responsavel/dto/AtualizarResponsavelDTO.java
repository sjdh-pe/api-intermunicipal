package br.gov.pe.sjdh.apiIntermunicipal.domain.responsavel.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * DTO de entrada para atualização de Responsável.
 * Todos os campos são opcionais para permitir atualização parcial.
 */
public record AtualizarResponsavelDTO(
        @Size(max = 80, message = "O nome pode ter no máximo 80 caracteres")
        String nome,

        @Pattern(regexp = "\\d{11}", message = "O CPF deve conter 11 dígitos numéricos")
        String cpf,

        @Size(max = 15, message = "O RG pode ter no máximo 15 caracteres")
        String rg,

        Boolean ativo
) {
}
