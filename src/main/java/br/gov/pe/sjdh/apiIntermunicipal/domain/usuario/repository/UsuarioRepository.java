package br.gov.pe.sjdh.apiIntermunicipal.domain.usuario.repository;

import br.gov.pe.sjdh.apiIntermunicipal.domain.usuario.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByCpf(String cpf);
    Optional<Usuario> findByEmail(String email);
}
