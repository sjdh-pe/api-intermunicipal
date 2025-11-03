package br.gov.pe.sjdh.apiIntermunicipal.domain.beneficiario.dto;


import br.gov.pe.sjdh.apiIntermunicipal.domain.endereco.DadosEnderecoDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;


import java.time.LocalDate;

public record CadastrarBeneficiarioDTO(

    @NotBlank(message = "O nome é obrigatório")
    @Size(max = 100, message = "O nome pode ter no máximo 100 caracteres")
    String nome,

    @NotBlank(message = "O nome da mãe é obrigatório")
    @Size(max = 100, message = "O nome da mãe pode ter no máximo 100 caracteres")
    String nomeMae,

    @NotBlank(message = "O CPF é obrigatório")
    String cpf,

    @NotBlank(message = "O RG é obrigatório")
    @Size(max = 20, message = "O RG pode ter no máximo 20 caracteres")
    String rg,

    @NotNull(message = "A data de nascimento é obrigatória")
    @Past(message = "A data de nascimento deve estar no passado")
    LocalDate dataNascimento,

    // Chaves estrangeiras
    @NotNull(message = "O sexo é obrigatório")
    @Positive(message = "ID de sexo inválido")
    Short sexoId,

    @NotNull(message = "A etnia é obrigatória")
    @Positive(message = "ID de etnia inválido")
    Short etniaId,

    @NotNull(message = "O tipo de deficiência é obrigatório")
    @Positive(message = "ID do tipo de deficiência inválido")
    Short tipoDeficienciaId,

    @NotNull(message = "O status do benefício é obrigatório")
    @Positive(message = "ID do status do benefício inválido")
    Short statusBeneficioId,

    @NotNull(message = "O local de retirada é obrigatório")
    @Positive(message = "ID do local de retirada inválido")
    Short localRetiradaId,

    @NotNull(message = "A cidade é obrigatória")
    @Positive(message = "ID da cidade inválido")
    Short cidadeId,

    // Contato
    @NotBlank(message = "O telefone é obrigatório")
    @Pattern(regexp = "\\d{8,15}", message = "Telefone deve conter apenas números (8 a 15 dígitos)")
    String telefone,

    @NotBlank(message = "O e-mail é obrigatório")
    @Email(message = "Formato de e-mail inválido")
    @Size(max = 100, message = "O e-mail pode ter no máximo 100 caracteres")
    String email,

    // Endereço
    @NotNull(message = "O endereço é obrigatório")
    @Valid // Permite validar os campos internos de DadosEndereco
    DadosEnderecoDTO endereco,

    @NotNull(message = "Informe se possui VEM Livre Acesso RMR")
    Boolean vemLivreAcessoRmr

) { }

