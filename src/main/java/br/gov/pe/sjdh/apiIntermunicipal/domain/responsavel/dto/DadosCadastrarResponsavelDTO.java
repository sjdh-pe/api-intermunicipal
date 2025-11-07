package br.gov.pe.sjdh.apiIntermunicipal.domain.responsavel.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * DTO de entrada para cadastro de Responsável.
 */
public record DadosCadastrarResponsavelDTO(
        @NotBlank(message = "{NotBlank}")
        @Size(max = 80, message = "{Size.responsavel.nome}")
        String nome,

        @NotBlank(message = "{NotBlank}")
        @Pattern(regexp = "\\d{11}", message = "{cpf.invalido}")
        String cpf,

        @NotBlank(message = "{NotBlank}")
        @Size(max = 15, message = "{Size.responsavel.rg}")
        String rg,

        // opcional: permite enviar explicitamente, caso contrário será true pela entidade
        Boolean ativo
) {
}
