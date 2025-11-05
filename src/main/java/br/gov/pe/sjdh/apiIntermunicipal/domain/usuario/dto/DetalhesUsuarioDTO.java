package br.gov.pe.sjdh.apiIntermunicipal.domain.usuario.dto;

import br.gov.pe.sjdh.apiIntermunicipal.domain.usuario.model.Usuario;

import java.time.OffsetDateTime;

/**
 * DTO de saída com detalhes de um Usuário.
 */
public record DetalhesUsuarioDTO(
        Integer id,
        String nome,
        String cpf,
        String email,
        boolean ativo,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {
    public DetalhesUsuarioDTO(Usuario u) {
        this(
                u.getId(),
                u.getNome(),
                u.getCpf(),
                u.getEmail(),
                u.isAtivo(),
                u.getCreatedAt(),
                u.getUpdatedAt()
        );
    }
}
