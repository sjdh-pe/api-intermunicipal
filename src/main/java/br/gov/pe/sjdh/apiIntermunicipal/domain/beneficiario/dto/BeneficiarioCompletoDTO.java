package br.gov.pe.sjdh.apiIntermunicipal.domain.beneficiario.dto;

import br.gov.pe.sjdh.apiIntermunicipal.domain.beneficiario.view.BeneficiarioCompletoView;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

public record BeneficiarioCompletoDTO(
        UUID id,
        String nome,
        String nomeMae,
        String cpf,
        String rg,
        LocalDate dataNascimento,
        Integer idade,
        String sexo,
        String etnia,
        String tipoDeficiencia,
        String statusBeneficio,
        String localRetirada,
        Boolean vemLivreAcessoRmr,
        String telefone,
        String email,
        String enderecoCompleto,
        String bairro,
        String cidade,
        String uf,
        String cep,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt,
        Boolean ativo
) {
    public BeneficiarioCompletoDTO(BeneficiarioCompletoView v) {
        this(
            v.getId(),
            v.getNome(),
            v.getNomeMae(),
            v.getCpf(),
            v.getRg(),
            v.getDataNascimento(),
            v.getIdade(),
            v.getSexo(),
            v.getEtnia(),
            v.getTipoDeficiencia(),
            v.getStatusBeneficio(),
            v.getLocalRetirada(),
            v.getVemLivreAcessoRmr(),
            v.getTelefone(),
            v.getEmail(),
            v.getEnderecoCompleto(),
            v.getBairro(),
            v.getCidade(),
            v.getUf(),
            v.getCep(),
            v.getCreatedAt(),
            v.getUpdatedAt(),
            v.getAtivo()
        );
    }
}