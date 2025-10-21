package br.gov.pe.sjdh.apiIntermunicipal.service;

import br.gov.pe.sjdh.apiIntermunicipal.domain.beneficiario.model.Beneficiario;
import br.gov.pe.sjdh.apiIntermunicipal.domain.beneficiario.dto.BeneficiarioCompletoDTO;
import br.gov.pe.sjdh.apiIntermunicipal.domain.beneficiario.repository.BeneficiarioCompletoViewRepository;
import br.gov.pe.sjdh.apiIntermunicipal.domain.beneficiario.repository.BeneficiarioRepository;
import br.gov.pe.sjdh.apiIntermunicipal.domain.beneficiario.dto.DadosCadastrarBeneficiarioDTO;
import br.gov.pe.sjdh.apiIntermunicipal.domain.beneficiario.dto.DetalhesBeneficiarioDTO;
import br.gov.pe.sjdh.apiIntermunicipal.domain.lookup.cidade.CidadeRepository;
import br.gov.pe.sjdh.apiIntermunicipal.domain.lookup.etnia.EtniaRepository;
import br.gov.pe.sjdh.apiIntermunicipal.domain.lookup.localRetirada.LocalRetiradaRepository;
import br.gov.pe.sjdh.apiIntermunicipal.domain.lookup.sexoBeneficiario.SexoBeneficiarioRepository;
import br.gov.pe.sjdh.apiIntermunicipal.domain.lookup.statusBeneficio.StatusBeneficiarioRepository;
import br.gov.pe.sjdh.apiIntermunicipal.domain.lookup.tipoDeficiencia.TipoDeficienciaRepository;
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
                       .orElseThrow(() -> new RuntimeException("Beneficiário não encontrado"));
   }


    public BeneficiarioCompletoDTO buscarPorCpfEDataNascimento(String cpf, String dataNascimento) {

        BeneficiarioCompletoDTO beneficiario =viewRepo.findByCpf(cpf)
                       .map(BeneficiarioCompletoDTO::new)
                       .orElseThrow(() -> new RuntimeException("Beneficiário não encontrado"));
        System.out.println(beneficiario.dataNascimento().toString());
        System.out.println(dataNascimento);
        if (!beneficiario.dataNascimento().toString().equals(dataNascimento)) {
            throw new RuntimeException("Beneficiário não encontrado, CPF ou data de nascimento inválidos.");
        }
        return beneficiario;

   }

    @Transactional
    public ResponseEntity<DetalhesBeneficiarioDTO> cadastrar(DadosCadastrarBeneficiarioDTO dto) {
        System.out.println("Service: " + dto);


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
    public void excluir(UUID id) {
       beneficiarioRepo.deleteById(id);
    }


}