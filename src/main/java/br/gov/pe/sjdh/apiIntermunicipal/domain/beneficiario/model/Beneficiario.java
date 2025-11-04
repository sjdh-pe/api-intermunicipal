package br.gov.pe.sjdh.apiIntermunicipal.domain.beneficiario.model;

import br.gov.pe.sjdh.apiIntermunicipal.domain.beneficiario.dto.CadastrarBeneficiarioDTO;
import br.gov.pe.sjdh.apiIntermunicipal.domain.endereco.Endereco;
import br.gov.pe.sjdh.apiIntermunicipal.domain.lookup.cidade.Cidade;
import br.gov.pe.sjdh.apiIntermunicipal.domain.lookup.etnia.Etnia;
import br.gov.pe.sjdh.apiIntermunicipal.domain.lookup.localRetirada.LocalRetirada;
import br.gov.pe.sjdh.apiIntermunicipal.domain.lookup.sexoBeneficiario.SexoBeneficiario;
import br.gov.pe.sjdh.apiIntermunicipal.domain.lookup.statusBeneficio.StatusBeneficiario;
import br.gov.pe.sjdh.apiIntermunicipal.domain.lookup.tipoDeficiencia.TipoDeficiencia;
import br.gov.pe.sjdh.apiIntermunicipal.domain.responsavel.model.Responsavel;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;


@Table(name = "beneficiarios")
@Entity(name = "Beneficiario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
public class Beneficiario {

    // ========================
    // IDENTIFICAÇÃO
    // ========================
    @Id
    @GeneratedValue(generator = "uuid2")
    @Column(updatable = false, nullable = false, columnDefinition = "uuid")
    private UUID id;

    @NotBlank
    @Column(length = 100, nullable = false)
    private String nome;

    @NotBlank
    @Column(name = "nome_mae", length = 100, nullable = false)
    private String nomeMae;

    @NotBlank
    //@CPF
    @Column(length = 11, unique = true, nullable = false)
    @Pattern(regexp = "\\d{11}", message = "O CPF deve conter 11 dígitos numéricos")
    private String cpf;

    @NotBlank
    @Column(length = 20, nullable = false)
    private String rg;

    @Past
    @Column(name = "data_nascimento", nullable = false)
    private LocalDate dataNascimento;

    // ========================
    // RELACIONAMENTOS
    // ========================
     @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "id_responsavel", nullable = true)
    private Responsavel responsavel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sexo_id", nullable = false)
    private SexoBeneficiario sexo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "etnia_id", nullable = false)
    private Etnia etnia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipo_deficiencia_id", nullable = false)
    private TipoDeficiencia tipoDeficiencia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_beneficio_id", nullable = false)
    private StatusBeneficiario statusBeneficio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "local_retirada_id", nullable = false)
    private LocalRetirada localRetirada;

    // ========================
    // CONTATO
    // ========================

    @NotBlank
    @Pattern(regexp = "^\\(?\\d{2}\\)?\\s?\\d{4,5}-?\\d{4}$", message = "Telefone inválido")
    @Column(length = 15, nullable = false)
    private String telefone;

    @Email
    @NotBlank
    @Column(length = 100, nullable = false)
    private String email;

    // ========================
    // ENDEREÇO EMBUTIDO
    // ========================

    @Valid
    @Embedded
    private Endereco endereco;

    // ========================
    // CAMPOS ADICIONAIS
    // ========================

    @Column(name = "vem_livre_acesso_rmr", nullable = false)
    private boolean vemLivreAcessoRmr = false;

    @Column(nullable = false)
    private boolean ativo = true;

    // ========================
    // METADADOS
    // ========================

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private OffsetDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    // ========================
    // CONSTRUTOR VIA DTO
    // ========================

    public Beneficiario(
            CadastrarBeneficiarioDTO dto,
            SexoBeneficiario sexo,
            Etnia etnia,
            TipoDeficiencia tipoDef,
            StatusBeneficiario status,
            LocalRetirada local,
            Cidade cidade
    ) {
        this.nome = dto.nome();
        this.nomeMae = dto.nomeMae();
        this.cpf = dto.cpf().replaceAll("\\D", "");
        this.rg = dto.rg();
        this.dataNascimento = dto.dataNascimento();
        this.telefone = dto.telefone();
        this.email = dto.email();
        this.sexo = sexo;
        this.etnia = etnia;
        this.tipoDeficiencia = tipoDef;
        this.statusBeneficio = status;
        this.localRetirada = local;
        this.vemLivreAcessoRmr = dto.vemLivreAcessoRmr();
        this.endereco = new Endereco(dto.endereco(), cidade);
    }
}