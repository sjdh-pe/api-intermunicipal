package br.gov.pe.sjdh.apiIntermunicipal.domain.responsavel.repository;

import br.gov.pe.sjdh.apiIntermunicipal.domain.responsavel.model.Responsavel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ResponsavelRepository extends JpaRepository<Responsavel, Integer> {
    Optional<Responsavel> findByCpf(String cpf);
}
