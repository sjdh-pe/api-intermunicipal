package br.gov.pe.sjdh.apiIntermunicipal.domain.lookup.etnia;


import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "etnias")
public class Etnia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // AUTO_INCREMENT
    private Short id;   // SMALLINT UNSIGNED cabe em Short (0–255)

    @Column(nullable = false, unique = true, length = 30)
    private String nome;

    @Column(nullable = false)
    private Boolean ativo = true;

    @Column(name = "created_at", updatable = false, insertable = false)
    private LocalDateTime createdAt;
}
