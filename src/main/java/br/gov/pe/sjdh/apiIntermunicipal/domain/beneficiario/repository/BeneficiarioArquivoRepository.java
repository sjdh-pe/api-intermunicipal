package br.gov.pe.sjdh.apiIntermunicipal.domain.beneficiario.repository;

import br.gov.pe.sjdh.apiIntermunicipal.domain.beneficiario.model.BeneficiarioArquivo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BeneficiarioArquivoRepository extends JpaRepository<BeneficiarioArquivo, Long> {
}
