package br.gov.pe.sjdh.apiIntermunicipal.domain.endereco;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record DadosEnderecoDTO(

        @NotBlank(message = "{NotBlank}")
    @Size(max = 200, message = "{Size.beneficiario.endereco}")
    String endereco,

    @NotBlank(message = "{NotBlank}")
    @Size(max = 80, message = "{Size.beneficiario.bairro}")
    String bairro,

    @Pattern(regexp = "\\d{5}-\\d{3}", message = "{Pattern.beneficiario.cep}")
    String cep,

    @Size(max = 10, message = "{Size.beneficiario.numero}")
    String numero,

    @Size(max = 50, message = "{Size.beneficiario.complemento}")
    String complemento,

    @Pattern(regexp = "^[A-Za-z]{2}$", message = "{Pattern.beneficiario.uf}")
    String uf
) { }