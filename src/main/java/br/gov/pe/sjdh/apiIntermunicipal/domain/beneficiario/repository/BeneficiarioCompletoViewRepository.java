package br.gov.pe.sjdh.apiIntermunicipal.domain.beneficiario.repository;

import br.gov.pe.sjdh.apiIntermunicipal.domain.beneficiario.dto.BeneficiarioCompletoDTO;
import br.gov.pe.sjdh.apiIntermunicipal.domain.beneficiario.view.BeneficiarioCompletoView;
import jakarta.annotation.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface BeneficiarioCompletoViewRepository extends JpaRepository<BeneficiarioCompletoView, UUID> {

    Page<BeneficiarioCompletoView> findAll(@Nullable Pageable paginas);

    Optional<BeneficiarioCompletoView> findById( UUID id);

    Optional<BeneficiarioCompletoView> findByCpf(String cpf);


}