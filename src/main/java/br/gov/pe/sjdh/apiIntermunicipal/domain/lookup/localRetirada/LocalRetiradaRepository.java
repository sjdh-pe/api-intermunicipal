package br.gov.pe.sjdh.apiIntermunicipal.domain.lookup.localRetirada;


import org.springframework.data.jpa.repository.JpaRepository;

public interface LocalRetiradaRepository extends JpaRepository<LocalRetirada, Short> {
    // consultas personalizadas, se necessário
}