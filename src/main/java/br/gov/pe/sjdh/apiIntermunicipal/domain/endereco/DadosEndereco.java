package br.gov.pe.sjdh.apiIntermunicipal.domain.endereco;


import br.gov.pe.sjdh.apiIntermunicipal.domain.lookup.cidade.Cidade;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record DadosEndereco(
        @NotBlank
        String endereco,

        @NotBlank
        String bairro,

        @NotBlank
        @Pattern(regexp = "\\d{5}-\\d{3}", message = "CEP deve estar no formato 00000-000")
        String cep,

        String numero,

        String complemento,


        @ManyToOne
        @JoinColumn(name = "cidade_id")
        Cidade cidade,

        @NotBlank
        String uf) {

}