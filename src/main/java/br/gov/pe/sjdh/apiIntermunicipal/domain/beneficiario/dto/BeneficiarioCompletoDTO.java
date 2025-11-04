package br.gov.pe.sjdh.apiIntermunicipal.domain.beneficiario.dto;

import br.gov.pe.sjdh.apiIntermunicipal.domain.beneficiario.view.BeneficiarioCompletoView;
import lombok.Builder;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * DTO usado para expor os dados da view vw_beneficiarios_detalhes
 * em formato JSON, mantendo os nomes amigáveis e a imutabilidade.
 */
@Builder
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


        OffsetDateTime createdAt,
        OffsetDateTime updatedAt,
        Boolean ativo

) {

    /**
     * Construtor de conversão da entidade da view (read-only)
     * para o DTO usado nas respostas da API.
     */
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

            v.getCreatedAt(),
            v.getUpdatedAt(),
            v.getAtivo()
        );
    }
}