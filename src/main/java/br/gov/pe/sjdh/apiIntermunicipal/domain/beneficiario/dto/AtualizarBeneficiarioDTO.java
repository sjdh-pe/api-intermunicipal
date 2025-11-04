package br.gov.pe.sjdh.apiIntermunicipal.domain.beneficiario.dto;

import br.gov.pe.sjdh.apiIntermunicipal.domain.endereco.DadosEnderecoDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;
import java.util.UUID;

/**
 * DTO utilizado para atualização parcial ou total de um Beneficiário existente.
 * Compatível com a entidade Beneficiario e o padrão REST da API Intermunicipal.
 */
public record AtualizarBeneficiarioDTO(

    @NotNull(message = "O ID do beneficiário é obrigatório para atualização")
    UUID id,

    @Size(max = 100, message = "O nome pode ter no máximo 100 caracteres")
    String nome,

    @Size(max = 100, message = "O nome da mãe pode ter no máximo 100 caracteres")
    String nomeMae,

    @CPF(message = "CPF inválido")
    @Size(max = 11, message = "O CPF deve ter 11 dígitos")
    String cpf,

    @Size(max = 20, message = "O RG pode ter no máximo 20 caracteres")
    String rg,

    @Past(message = "A data de nascimento deve estar no passado")
    LocalDate dataNascimento,

    // Chaves estrangeiras (permitem atualização individual)
    @Positive(message = "ID de sexo inválido")
    Short sexoId,

    @Positive(message = "ID de etnia inválido")
    Short etniaId,

    @Positive(message = "ID do tipo de deficiência inválido")
    Short tipoDeficienciaId,

    @Positive(message = "ID do status do benefício inválido")
    Short statusBeneficioId,

    @Positive(message = "ID do local de retirada inválido")
    Short localRetiradaId,

    @Positive(message = "ID da cidade inválido")
    Short cidadeId,

    // Contato
    @Pattern(regexp = "\\d{8,15}", message = "Telefone deve conter apenas números (8 a 15 dígitos)")
    String telefone,

    @Email(message = "Formato de e-mail inválido")
    @Size(max = 100, message = "O e-mail pode ter no máximo 100 caracteres")
    String email,

    // Endereço
    @Valid
    DadosEnderecoDTO endereco,

    // Outras informações
    Boolean vemLivreAcessoRmr,

    Boolean ativo

) { }