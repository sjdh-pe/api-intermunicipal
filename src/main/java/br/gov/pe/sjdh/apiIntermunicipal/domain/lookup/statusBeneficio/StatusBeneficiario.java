package br.gov.pe.sjdh.apiIntermunicipal.domain.lookup.statusBeneficio;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "status_beneficio")
public class StatusBeneficiario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Short id; // SMALLINT UNSIGNED cabe em Short

    @Column(nullable = false, unique = true, length = 30)
    private String nome;

    @Column(length = 100)
    private String descricao;

    @Column(nullable = false)
    private Boolean ativo = true;

    @Column(name = "created_at", updatable = false, insertable = false)
    private LocalDateTime createdAt;

}
