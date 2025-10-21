package br.gov.pe.sjdh.apiIntermunicipal.domain.lookup.tipoDeficiencia;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "tipos_deficiencia")
public class TipoDeficiencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // AUTO_INCREMENT
    private Short id;   // ou Integer, pode ser SMALLINT/SMALLINT dependendo do banco

    @Column(nullable = false, unique = true, length = 50)
    private String nome;

    @Column(length = 150)
    private String descricao;

    @Column(nullable = false)
    private Boolean ativo = true;

    @Column(name = "created_at", updatable = false, insertable = false)
    private LocalDateTime createdAt;

    public TipoDeficiencia() {

    }

}
