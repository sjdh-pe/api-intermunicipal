package br.gov.pe.sjdh.apiIntermunicipal.domain.endereco;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record DadosEnderecoDTO(

        @NotBlank(message = "O logradouro é obrigatório")
    @Size(max = 200, message = "O logradouro pode ter no máximo 200 caracteres")
    String endereco,

    @NotBlank(message = "O bairro é obrigatório")
    @Size(max = 80, message = "O bairro pode ter no máximo 80 caracteres")
    String bairro,

    @Pattern(regexp = "\\d{5}-\\d{3}", message = "O CEP deve estar no formato 00000-000")
    String cep,

    @Size(max = 10, message = "O número pode ter no máximo 10 caracteres")
    String numero,

    @Size(max = 50, message = "O complemento pode ter no máximo 50 caracteres")
    String complemento,

    @Size(min = 2, max = 2, message = "UF deve conter exatamente 2 letras")
    String uf
) { }