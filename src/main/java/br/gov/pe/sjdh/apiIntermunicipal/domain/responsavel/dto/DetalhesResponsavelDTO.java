package br.gov.pe.sjdh.apiIntermunicipal.domain.responsavel.dto;

import br.gov.pe.sjdh.apiIntermunicipal.domain.responsavel.model.Responsavel;

import java.time.OffsetDateTime;

/**
 * DTO de saída com detalhes de um Responsável.
 */
public record DetalhesResponsavelDTO(
        Integer id,
        String nome,
        String cpf,
        String rg,
        boolean ativo,
        Integer quantidadeBeneficiarios,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {
    public DetalhesResponsavelDTO(Responsavel r) {
        this(
                r.getId(),
                r.getNome(),
                r.getCpf(),
                r.getRg(),
                r.isAtivo(),
                r.getBeneficiarios() != null ? r.getBeneficiarios().size() : 0,
                r.getCreatedAt(),
                r.getUpdatedAt()
        );
    }
}
