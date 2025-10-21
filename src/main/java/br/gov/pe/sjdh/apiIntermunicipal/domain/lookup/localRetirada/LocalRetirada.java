package br.gov.pe.sjdh.apiIntermunicipal.domain.lookup.localRetirada;


import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "locais_retirada")
public class LocalRetirada {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Short id;  // SMALLINT UNSIGNED cabe em Short (0 a 255)

    @Column(nullable = false, unique = true, length = 50)
    private String nome;

    @Column(length = 150)
    private String endereco;

    @Column(length = 20)
    private String telefone;

    @Column(name = "horario_funcionamento", length = 100)
    private String horarioFuncionamento;

    @Column(nullable = false)
    private Boolean ativo = true;

    @Column(name = "created_at", updatable = false, insertable = false)
    private LocalDateTime createdAt;

}
