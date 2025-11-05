package br.gov.pe.sjdh.apiIntermunicipal.domain.responsavel.dto;

import br.gov.pe.sjdh.apiIntermunicipal.domain.responsavel.model.Responsavel;

/**
 * DTO de saída resumido para listagens de Responsáveis.
 */
public record ResponsavelResumoDTO(
        Integer id,
        String nome,
        String cpf,
        String rg,
        boolean ativo
) {
    public ResponsavelResumoDTO(Responsavel r) {
        this(
                r.getId(),
                r.getNome(),
                r.getCpf(),
                r.getRg(),
                r.isAtivo()
        );
    }
}
