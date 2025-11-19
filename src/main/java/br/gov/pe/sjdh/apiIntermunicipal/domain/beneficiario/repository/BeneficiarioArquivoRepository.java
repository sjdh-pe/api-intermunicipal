package br.gov.pe.sjdh.apiIntermunicipal.domain.beneficiario.repository;

import br.gov.pe.sjdh.apiIntermunicipal.domain.beneficiario.model.BeneficiarioArquivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface BeneficiarioArquivoRepository extends JpaRepository<BeneficiarioArquivo, Long> {
    List<BeneficiarioArquivo> findByBeneficiario_IdAndAtivoTrue(UUID beneficiarioId);

    @Query("select ba from BeneficiarioArquivo ba join fetch ba.tipoArquivo where ba.beneficiario.id = :beneficiarioId and ba.ativo = true")
    List<BeneficiarioArquivo> findAtivosComTipoByBeneficiarioId(@Param("beneficiarioId") UUID beneficiarioId);

    // Foto do beneficiário: retorna o registro ativo mais recente para um determinado tipo de arquivo
    java.util.Optional<BeneficiarioArquivo> findTop1ByBeneficiario_IdAndTipoArquivo_IdAndAtivoTrueOrderByCreatedAtDesc(
            UUID beneficiarioId, Short tipoArquivoId
    );
}
