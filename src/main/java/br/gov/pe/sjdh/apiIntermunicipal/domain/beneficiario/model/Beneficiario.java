package br.gov.pe.sjdh.apiIntermunicipal.domain.beneficiario.model;


import br.gov.pe.sjdh.apiIntermunicipal.domain.beneficiario.dto.DadosCadastrarBeneficiarioDTO;
import br.gov.pe.sjdh.apiIntermunicipal.domain.endereco.Endereco;
import br.gov.pe.sjdh.apiIntermunicipal.domain.lookup.cidade.Cidade;
import br.gov.pe.sjdh.apiIntermunicipal.domain.lookup.etnia.Etnia;
import br.gov.pe.sjdh.apiIntermunicipal.domain.lookup.localRetirada.LocalRetirada;
import br.gov.pe.sjdh.apiIntermunicipal.domain.lookup.sexoBeneficiario.SexoBeneficiario;
import br.gov.pe.sjdh.apiIntermunicipal.domain.lookup.statusBeneficio.StatusBeneficiario;
import br.gov.pe.sjdh.apiIntermunicipal.domain.lookup.tipoDeficiencia.TipoDeficiencia;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;


import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

@Table(name = "beneficiarios")
@Entity(name = "Beneficiario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Beneficiario {

    @Id
    @GeneratedValue(generator = "uuid2")
    @Column(updatable = false, nullable = false, columnDefinition = "uuid")
    private UUID id;

    @NotBlank
    @Column(length = 100)
    private String nome;

    @NotBlank
    @Column(length = 100)
    private String nomeMae;

    @NotBlank
    //@CPF
    @Column(length = 11, unique = true)
    private String cpf;

    @NotBlank
    @Column(length = 20)
    private String rg;

    @Past
    private LocalDate dataNascimento;


    @NotBlank
    @Pattern(regexp = "^\\(?\\d{2}\\)?\\s?\\d{4,5}-?\\d{4}$", message = "Telefone inválido")
    @Column(length = 15)
    private String telefone;

    @Email
    @NotBlank
    @Column(length = 100)
    private String email;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipo_deficiencia_id", nullable = false)
    private TipoDeficiencia tipoDeficiencia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "local_retirada_id", nullable = false)
    private LocalRetirada localRetirada;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sexo_id", nullable = false)
    private SexoBeneficiario sexo;

    @ManyToOne
    @JoinColumn(name = "etnia_id")
    private Etnia etnia;

    private boolean vemLivreAcessoRmr;

    @Valid
    @Embedded
    private Endereco endereco;

    // Caminhos dos arquivos salvos no disco
    @Column(name = "path_rg", nullable = true)
    private String pathRg;

    @Column(name = "path_cpf", nullable = true)
    private String pathCpf;

    @Column(name = "path_comprovante_endereco", nullable = true)
    private String pathComprovanteEndereco;

    @Column(name = "path_foto", nullable = true)
    private String pathFoto;

    @Column(name = "path_laudo_medico", nullable = true)
    private String pathLaudoMedico;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_beneficio_id", nullable = false)
    private StatusBeneficiario statusBeneficio;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private OffsetDateTime createAt;


    public Beneficiario(DadosCadastrarBeneficiarioDTO dto,
                        SexoBeneficiario sexo,
                        Etnia etnia,
                        TipoDeficiencia tipoDef,
                        StatusBeneficiario status,
                        LocalRetirada local,
                        Cidade cidade) {
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
        this.pathRg = null;
        this.pathCpf = null;
        this.pathComprovanteEndereco = null;
        this.pathFoto = null;
        this.pathLaudoMedico = null;
    }

    public Beneficiario(DadosCadastrarBeneficiarioDTO dto, SexoBeneficiario sexo, Etnia etnia, TipoDeficiencia tipoDef, StatusBeneficiario status, LocalRetirada local, Endereco endereco) {
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
        this.endereco = endereco;
        this.pathRg = null;
        this.pathCpf = null;
        this.pathComprovanteEndereco = null;
        this.pathFoto = null;
        this.pathLaudoMedico = null;
    }
}

