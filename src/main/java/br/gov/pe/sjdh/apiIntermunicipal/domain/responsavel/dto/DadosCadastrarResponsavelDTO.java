package br.gov.pe.sjdh.apiIntermunicipal.domain.responsavel.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * DTO de entrada para cadastro de Responsável.
 */
public record DadosCadastrarResponsavelDTO(
        @NotBlank(message = "O nome é obrigatório")
        @Size(max = 80, message = "O nome pode ter no máximo 80 caracteres")
        String nome,

        @NotBlank(message = "O CPF é obrigatório")
        @Pattern(regexp = "\\d{11}", message = "O CPF deve conter 11 dígitos numéricos")
        String cpf,

        @NotBlank(message = "O RG é obrigatório")
        @Size(max = 15, message = "O RG pode ter no máximo 15 caracteres")
        String rg,

        // opcional: permite enviar explicitamente, caso contrário será true pela entidade
        Boolean ativo
) {
}
