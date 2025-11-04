package br.gov.pe.sjdh.apiIntermunicipal.domain.beneficiario.view;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * Representa a view vw_beneficiarios_detalhes,
 * que agrega dados detalhados do beneficiário,
 * incluindo idade calculada, endereço formatado
 * e nomes resolvidos das tabelas lookup.
 */
@Entity
@Table(name = "vw_beneficiarios_detalhes")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Immutable
public class BeneficiarioCompletoView {

    @Id
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    private String nome;

    @Column(name = "nome_mae")
    private String nomeMae;

    private String cpf;
    private String rg;

    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;

    // Idade derivada (calculada na view)
    private Integer idade;

    // Campos de referência resolvidos
    private String sexo;
    private String etnia;

    @Column(name = "tipo_deficiencia")
    private String tipoDeficiencia;

    @Column(name = "status_beneficio")
    private String statusBeneficio;

    @Column(name = "local_retirada")
    private String localRetirada;

    @Column(name = "vem_livre_acesso_rmr")
    private Boolean vemLivreAcessoRmr;

    // Contato
    private String telefone;
    private String email;

    // Endereço formatado e campos individuais
    @Column(name = "endereco_completo")
    private String enderecoCompleto;

    private String bairro;
    private String cidade;

    @Column(name = "uf")
    private String uf;


    // Metadados
    @Column(name = "created_at", updatable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    private Boolean ativo;
}