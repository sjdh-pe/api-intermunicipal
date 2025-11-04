package br.gov.pe.sjdh.apiIntermunicipal.domain.beneficiario.dto;

import br.gov.pe.sjdh.apiIntermunicipal.domain.beneficiario.model.Beneficiario;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;


public record DetalhesBeneficiarioDTO(
    UUID id,
    String nome,
    String nomeMae,
    String cpf,
    String rg,
    LocalDate dataNascimento,
    String telefone,
    String email,

    // Relacionamentos resolvidos para strings simples
    Short sexoId,
    String sexo,
    Short etniaId,
    String etnia,
    Short tipoDeficienciaId,
    String tipoDeficiencia,
    Short statusBeneficioId,
    String statusBeneficio,
    Short localRetiradaId,
    String localRetirada,

    Boolean vemLivreAcessoRmr,

    // Endereço
    String logradouro,
    String bairro,
    String cep,
    String numero,
    String complemento,
    Short cidadeId,
    String cidade,
    String uf,


    OffsetDateTime updatedAt,
    OffsetDateTime createdAt
) {
    public DetalhesBeneficiarioDTO(Beneficiario b) {
        this(
            b.getId(),
            b.getNome(),
            b.getNomeMae(),
            b.getCpf(),
            b.getRg(),
            b.getDataNascimento(),
            b.getTelefone(),
            b.getEmail(),

            b.getSexo() != null ? b.getSexo().getId() : null,
            b.getSexo() != null ? b.getSexo().getNome() : null,
            b.getEtnia() != null ? b.getEtnia().getId() : null,
            b.getEtnia() != null ? b.getEtnia().getNome() : null,
            b.getTipoDeficiencia() != null ? b.getTipoDeficiencia().getId() : null,
            b.getTipoDeficiencia() != null ? b.getTipoDeficiencia().getNome() : null,
            b.getStatusBeneficio() != null ? b.getStatusBeneficio().getId() : null,
            b.getStatusBeneficio() != null ? b.getStatusBeneficio().getNome() : null,
            b.getLocalRetirada() != null ? b.getLocalRetirada().getId() : null,
            b.getLocalRetirada() != null ? b.getLocalRetirada().getNome() : null,

            b.isVemLivreAcessoRmr(),

            b.getEndereco() != null ? b.getEndereco().getEndereco() : null,
            b.getEndereco() != null ? b.getEndereco().getBairro() : null,
            b.getEndereco() != null ? b.getEndereco().getCep() : null,
            b.getEndereco() != null ? b.getEndereco().getNumero() : null,
            b.getEndereco() != null ? b.getEndereco().getComplemento() : null,
            b.getEndereco() != null && b.getEndereco().getCidade() != null ? b.getEndereco().getCidade().getId() : null,
            b.getEndereco() != null && b.getEndereco().getCidade() != null ? b.getEndereco().getCidade().getNome() : null,
            b.getEndereco() != null ? b.getEndereco().getUf() : null,
            b.getUpdatedAt(),
            b.getCreatedAt()
        );
    }
}