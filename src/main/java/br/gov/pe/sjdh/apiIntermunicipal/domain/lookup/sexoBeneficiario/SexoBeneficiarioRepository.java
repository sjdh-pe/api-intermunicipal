package br.gov.pe.sjdh.apiIntermunicipal.domain.lookup.sexoBeneficiario;


import org.springframework.data.jpa.repository.JpaRepository;

public interface SexoBeneficiarioRepository extends JpaRepository<SexoBeneficiario, Short> {
    // consultas personalizadas, se necessário

}