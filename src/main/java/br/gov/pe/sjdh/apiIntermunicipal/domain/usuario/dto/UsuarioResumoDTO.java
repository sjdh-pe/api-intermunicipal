package br.gov.pe.sjdh.apiIntermunicipal.domain.usuario.dto;

import br.gov.pe.sjdh.apiIntermunicipal.domain.usuario.model.Usuario;

/**
 * DTO de saída resumido para listagens de Usuários.
 */
public record UsuarioResumoDTO(
        Integer id,
        String nome,
        String cpf,
        String email,
        boolean ativo
) {
    public UsuarioResumoDTO(Usuario u) {
        this(
                u.getId(),
                u.getNome(),
                u.getCpf(),
                u.getEmail(),
                u.isAtivo()
        );
    }
}
