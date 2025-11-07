package br.gov.pe.sjdh.apiIntermunicipal.service;

import br.gov.pe.sjdh.apiIntermunicipal.domain.beneficiario.model.Beneficiario;
import br.gov.pe.sjdh.apiIntermunicipal.domain.beneficiario.dto.BeneficiarioCompletoDTO;
import br.gov.pe.sjdh.apiIntermunicipal.domain.beneficiario.repository.BeneficiarioCompletoViewRepository;
import br.gov.pe.sjdh.apiIntermunicipal.domain.beneficiario.repository.BeneficiarioRepository;
import br.gov.pe.sjdh.apiIntermunicipal.domain.beneficiario.dto.CadastrarBeneficiarioDTO;
import br.gov.pe.sjdh.apiIntermunicipal.domain.beneficiario.dto.DetalhesBeneficiarioDTO;
import br.gov.pe.sjdh.apiIntermunicipal.domain.beneficiario.dto.AtualizarBeneficiarioDTO;
import br.gov.pe.sjdh.apiIntermunicipal.domain.endereco.Endereco;
import br.gov.pe.sjdh.apiIntermunicipal.domain.lookup.cidade.CidadeRepository;
import br.gov.pe.sjdh.apiIntermunicipal.domain.lookup.etnia.EtniaRepository;
import br.gov.pe.sjdh.apiIntermunicipal.domain.lookup.localRetirada.LocalRetiradaRepository;
import br.gov.pe.sjdh.apiIntermunicipal.domain.lookup.sexoBeneficiario.SexoBeneficiarioRepository;
import br.gov.pe.sjdh.apiIntermunicipal.domain.lookup.statusBeneficio.StatusBeneficiarioRepository;
import br.gov.pe.sjdh.apiIntermunicipal.domain.lookup.tipoDeficiencia.TipoDeficienciaRepository;
import br.gov.pe.sjdh.apiIntermunicipal.infra.exception.NotFoundException;
import br.gov.pe.sjdh.apiIntermunicipal.infra.exception.BusinessException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.UUID;


@Service
@RequiredArgsConstructor
public class BeneficiarioService {

    private final BeneficiarioRepository beneficiarioRepo;
    private final SexoBeneficiarioRepository sexoRepo;
    private final EtniaRepository etniaRepo;
    private final TipoDeficienciaRepository tipoRepo;
    private final StatusBeneficiarioRepository statusRepo;
    private final LocalRetiradaRepository localRepo;
    private final CidadeRepository cidadeRepo;
    private final BeneficiarioCompletoViewRepository viewRepo;

   public Page<BeneficiarioCompletoDTO> listar(@PageableDefault(
           sort = "updatedAt",
           direction = Sort.Direction.DESC,
                size = 20
        ) Pageable pageable){
       return viewRepo.findAll( pageable)
            .map(BeneficiarioCompletoDTO::new);
   }

   public BeneficiarioCompletoDTO detalhar(UUID id) {
        return viewRepo.findById(id)
                       .map(BeneficiarioCompletoDTO::new)
                       .orElseThrow(() -> new NotFoundException("Beneficiário não encontrado"));
   }


    public BeneficiarioCompletoDTO buscarPorCpfEDataNascimento(String cpf, String dataNascimento) {

        BeneficiarioCompletoDTO beneficiario =viewRepo.findByCpf(cpf)
                       .map(BeneficiarioCompletoDTO::new)
                       .orElseThrow(() -> new NotFoundException("Beneficiário não encontrado"));
        if (!beneficiario.dataNascimento().toString().equals(dataNascimento)) {
            throw new NotFoundException("Beneficiário não encontrado, CPF ou data de nascimento inválidos.");
        }
        return beneficiario;

   }

    @Transactional
    public ResponseEntity<DetalhesBeneficiarioDTO> cadastrar(CadastrarBeneficiarioDTO dto) {

       beneficiarioRepo.findByCpf(dto.cpf()).ifPresent(x -> {
            throw new BusinessException("CPF já cadastrado para outro beneficiário");
        });

        var sexo = sexoRepo.getReferenceById(dto.sexoId());
        var etnia = etniaRepo.getReferenceById(dto.etniaId());
        var tipoDef = tipoRepo.getReferenceById(dto.tipoDeficienciaId());
        var status = statusRepo.getReferenceById(dto.statusBeneficioId());
        var local = localRepo.getReferenceById(dto.localRetiradaId());
        var cidade = cidadeRepo.getReferenceById(dto.cidadeId());

        Beneficiario beneficiario = new Beneficiario(dto, sexo, etnia, tipoDef, status, local, cidade);
        beneficiarioRepo.save(beneficiario);

         // monta a URI do novo recurso
    var uri = ServletUriComponentsBuilder
            .fromCurrentRequest()           // pega a URL atual (/beneficiarios)
            .path("/{id}")                  // adiciona /{id}
            .buildAndExpand(beneficiario.getId()) // substitui {id} pelo ID do novo beneficiário
            .toUri();

        return ResponseEntity.created(uri).body(new DetalhesBeneficiarioDTO(beneficiario));
    }

    @Transactional
    public DetalhesBeneficiarioDTO atualizar(UUID id, AtualizarBeneficiarioDTO dto) {
        // Carrega o agregado com relacionamentos necessários
        Beneficiario b = beneficiarioRepo.detalharById(id)
                .orElseThrow(() -> new NotFoundException("Beneficiário não encontrado"));

        // Campos simples
        if (dto.nome() != null) b.setNome(dto.nome());
        if (dto.nomeMae() != null) b.setNomeMae(dto.nomeMae());
        if (dto.rg() != null) b.setRg(dto.rg());
        if (dto.dataNascimento() != null) b.setDataNascimento(dto.dataNascimento());
        if (dto.telefone() != null) b.setTelefone(dto.telefone());
        if (dto.email() != null) b.setEmail(dto.email());
        if (dto.vemLivreAcessoRmr() != null) b.setVemLivreAcessoRmr(dto.vemLivreAcessoRmr());
        if (dto.ativo() != null) b.setAtivo(dto.ativo());

        // CPF (normaliza e valida unicidade se alterado)
        if (dto.cpf() != null) {
            String novoCpf = dto.cpf().replaceAll("\\D", "");
            if (!novoCpf.equals(b.getCpf())) {
                if (beneficiarioRepo.existsByCpfAndIdNot(novoCpf, b.getId())) {
                    throw new BusinessException("Já existe beneficiário cadastrado com este CPF");
                }
                b.setCpf(novoCpf);
            }
        }

        // Relacionamentos (apenas se IDs informados)
        if (dto.sexoId() != null) b.setSexo(sexoRepo.getReferenceById(dto.sexoId()));
        if (dto.etniaId() != null) b.setEtnia(etniaRepo.getReferenceById(dto.etniaId()));
        if (dto.tipoDeficienciaId() != null) b.setTipoDeficiencia(tipoRepo.getReferenceById(dto.tipoDeficienciaId()));
        if (dto.statusBeneficioId() != null) b.setStatusBeneficio(statusRepo.getReferenceById(dto.statusBeneficioId()));
        if (dto.localRetiradaId() != null) b.setLocalRetirada(localRepo.getReferenceById(dto.localRetiradaId()));

        // Endereço e cidade
        if (dto.endereco() != null || dto.cidadeId() != null) {
            var atual = b.getEndereco();
            var cidade = dto.cidadeId() != null ? cidadeRepo.getReferenceById(dto.cidadeId())
                    : (atual != null ? atual.getCidade() : null);

            String cep = dto.endereco() != null && dto.endereco().cep() != null ? dto.endereco().cep() : (atual != null ? atual.getCep() : null);
            String logradouro = dto.endereco() != null && dto.endereco().endereco() != null ? dto.endereco().endereco() : (atual != null ? atual.getEndereco() : null);
            String numero = dto.endereco() != null && dto.endereco().numero() != null ? dto.endereco().numero() : (atual != null ? atual.getNumero() : null);
            String complemento = dto.endereco() != null && dto.endereco().complemento() != null ? dto.endereco().complemento() : (atual != null ? atual.getComplemento() : null);
            String bairro = dto.endereco() != null && dto.endereco().bairro() != null ? dto.endereco().bairro() : (atual != null ? atual.getBairro() : null);
            String uf = dto.endereco() != null && dto.endereco().uf() != null ? dto.endereco().uf() : (atual != null ? atual.getUf() : null);

            b.setEndereco(new Endereco(cep, logradouro, numero, complemento, bairro, cidade, uf));
        }

        // Persistência automática pelo contexto @Transactional
        return new DetalhesBeneficiarioDTO(b);
    }

    @Transactional
    public void excluir(UUID id) {
       beneficiarioRepo.deleteById(id);
    }


}