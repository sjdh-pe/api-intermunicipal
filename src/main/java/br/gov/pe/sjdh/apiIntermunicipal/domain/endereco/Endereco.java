package br.gov.pe.sjdh.apiIntermunicipal.domain.endereco;

import br.gov.pe.sjdh.apiIntermunicipal.domain.lookup.cidade.Cidade;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
public class Endereco {

    @Column(length = 9)
    @Pattern(regexp = "^\\d{5}-?\\d{3}$", message = "CEP inválido")
    private String cep;

    @Column(length = 200)
    @NotBlank
    private String endereco;

    @Column(length = 10)
    private String numero;

    @Column(length = 50)
    private String complemento;

    @NotBlank
    @Column(length = 80)
    private String bairro;

    @ManyToOne
    @JoinColumn(name = "cidade_id")
    private Cidade cidade;

    @NotBlank
    @Column(length = 2)
    private String uf;

    public Endereco(String cep,
                String endereco,
                String numero,
                String complemento,
                String bairro,
                Cidade cidade,
                String uf) {
        this.cep = cep;
        this.endereco = endereco;
        this.numero = numero;
        this.complemento = complemento;
        this.bairro = bairro;
        this.cidade = cidade;
        this.uf = uf;
    }

    public Endereco(DadosEnderecoDTO dados, Cidade cidade) {
        this(dados.cep(),
                dados.endereco(),
                dados.numero(),
                dados.complemento(),
                dados.bairro(),
                cidade,
            dados.uf());
    }
}
