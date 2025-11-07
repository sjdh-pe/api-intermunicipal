package br.gov.pe.sjdh.apiIntermunicipal.domain.beneficiario.repository;

import br.gov.pe.sjdh.apiIntermunicipal.domain.beneficiario.model.Beneficiario;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface BeneficiarioRepository extends JpaRepository<Beneficiario, UUID> {

    @Query("""
    SELECT b FROM Beneficiario b
    LEFT JOIN FETCH b.sexo
    LEFT JOIN FETCH b.etnia
    LEFT JOIN FETCH b.tipoDeficiencia
    LEFT JOIN FETCH b.statusBeneficio
    LEFT JOIN FETCH b.localRetirada
    LEFT JOIN FETCH b.endereco e
    LEFT JOIN FETCH e.cidade
    WHERE b.id = :id
""")
    Optional<Beneficiario> detalharById(@Param("id") UUID id);

    boolean existsByCpfAndIdNot(String cpf, UUID id);

    Optional<Object> findByCpf(@NotBlank(message = "{NotBlank.beneficiario.cpf}") String cpf);
}
