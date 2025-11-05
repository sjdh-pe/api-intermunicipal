package br.gov.pe.sjdh.apiIntermunicipal.domain.responsavel.model;

import br.gov.pe.sjdh.apiIntermunicipal.domain.beneficiario.model.Beneficiario;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.OffsetDateTime;
import java.util.List;

@Entity
@Table(name = "responsaveis")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
public class Responsavel {

    // ========================
    // IDENTIFICAÇÃO
    // ========================
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @Size(max = 80)
    @Column(length = 80, nullable = false)
    private String nome;

    @NotBlank
    @Pattern(regexp = "\\d{11}", message = "O CPF deve conter 11 dígitos numéricos")
    @Column(length = 11, unique = true, nullable = false)
    private String cpf;

    @NotBlank
    @Size(max = 15)
    @Column(length = 15, nullable = false)
    private String rg;

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
    // RELACIONAMENTOS
    // ========================
    @OneToMany(mappedBy = "responsavel", fetch = FetchType.EAGER)
private List<Beneficiario> beneficiarios;
}