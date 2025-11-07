package br.gov.pe.sjdh.apiIntermunicipal.domain.beneficiario.dto;


import br.gov.pe.sjdh.apiIntermunicipal.domain.endereco.DadosEnderecoDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;


import java.time.LocalDate;

public record CadastrarBeneficiarioDTO(

    @NotBlank(message = "{NotBlank.beneficiario.nome}")
    @Size(max = 100, message = "{Size.beneficiario.nome}")
    String nome,

    @NotBlank(message = "{NotBlank.beneficiario.nomeMae}")
    @Size(max = 100, message = "{Size.beneficiario.nomeMae}")
    String nomeMae,

    @NotBlank(message = "{NotBlank.beneficiario.cpf}")
    String cpf,

    @NotBlank(message = "{NotBlank.beneficiario.rg}")
    @Size(max = 20, message = "{Size.beneficiario.rg}")
    String rg,

    @NotNull(message = "{NotNull.beneficiario.dataNascimento}")
    @Past
    LocalDate dataNascimento,

    // Chaves estrangeiras
    @NotNull(message = "{NotNull.beneficiario.sexo}")
    @Positive
    Short sexoId,

    @NotNull(message = "{NotNull.beneficiario.etnia}")
    @Positive
    Short etniaId,

    @NotNull(message = "{NotNull.beneficiario.tipoDeficiencia}")
    @Positive
    Short tipoDeficienciaId,

    @NotNull(message = "{NotNull.beneficiario.statusBeneficio}")
    @Positive
    Short statusBeneficioId,

    @NotNull(message = "{NotNull.beneficiario.localRetirada}")
    @Positive
    Short localRetiradaId,

    @NotNull
    @Positive
    Short cidadeId,

    // Contato
    @NotBlank(message = "{NotBlank.beneficiario.telefone}")
    @Pattern(regexp = "^\\(?\\d{2}\\)?\\s?\\d{4,5}-?\\d{4}$", message = "{Pattern.beneficiario.telefone}")
    String telefone,

    @NotBlank(message = "{NotBlank.beneficiario.email}")
    @Email(message = "{Email.beneficiario.email}")
    @Size(max = 100, message = "{Size.beneficiario.email}")
    String email,

    // Endereço
    @NotNull(message = "{NotNull.beneficiario.endereco}")
    @Valid // Permite validar os campos internos de DadosEndereco
    DadosEnderecoDTO endereco,

    @NotNull(message = "{NotNull.beneficiario.vemLivreAcessoRmr}")
    Boolean vemLivreAcessoRmr

) { }

