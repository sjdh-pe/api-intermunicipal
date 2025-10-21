package br.gov.pe.sjdh.apiIntermunicipal.domain.lookup.cidade;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
@Table(name = "cidades")
@Entity(name = "cidade")
public class Cidade {

    @Id
    // Não use GenerationType.IDENTITY porque a tabela já possui IDs pré-definidos.
    // Os inserts do Flyway atribuem os códigos manualmente.
    @Column(nullable = false)
    private Short id;

    @Column(nullable = false, unique = true, length = 80)
    private String nome;

    @Column(nullable = false, length = 2)
    private String uf = "PE";

    @Column(nullable = false)
    private Boolean ativo = true;

    @Column(name = "created_at", updatable = false, insertable = false)
    private LocalDateTime createdAt;


}