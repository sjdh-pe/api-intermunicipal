package br.gov.pe.sjdh.apiIntermunicipal.domain.beneficiario.model;

import br.gov.pe.sjdh.apiIntermunicipal.domain.lookup.tipoArquivo.TipoArquivo;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;

/**
 * Representa um arquivo associado a um beneficiário (RG, CPF, Laudo, etc.)
 * conforme tabela beneficiario_arquivo.
 */
@Entity
@Table(name = "beneficiario_arquivo")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class BeneficiarioArquivo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Beneficiário associado (UUID binário)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "beneficiario_id", nullable = false)
    private Beneficiario beneficiario;

    // Tipo do arquivo (ex: RG, CPF, Laudo, etc.)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipo_arquivo_id", nullable = false)
    private TipoArquivo tipoArquivo;

    // Caminho físico no disco ou bucket de armazenamento
    @Column(name = "path", length = 255, nullable = false)
    private String path;

    @Column(nullable = false)
    private boolean ativo = true;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private OffsetDateTime createdAt;
}