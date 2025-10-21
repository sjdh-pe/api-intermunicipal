package br.gov.pe.sjdh.apiIntermunicipal.domain.lookup.sexoBeneficiario;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "sexos")
public class SexoBeneficiario {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Short id;   // SMALLINT UNSIGNED cabe em Short (0–255)

    @Column(nullable = false, unique = true, length = 20)
    private String nome;

    @Column(nullable = false)
    private Boolean ativo = true;

     @Column(name = "created_at", updatable = false, insertable = false)
    private LocalDateTime createdAt;

}
