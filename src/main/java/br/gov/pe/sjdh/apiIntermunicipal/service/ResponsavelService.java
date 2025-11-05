package br.gov.pe.sjdh.apiIntermunicipal.service;

import br.gov.pe.sjdh.apiIntermunicipal.domain.responsavel.dto.AtualizarResponsavelDTO;
import br.gov.pe.sjdh.apiIntermunicipal.domain.responsavel.dto.DadosCadastrarResponsavelDTO;
import br.gov.pe.sjdh.apiIntermunicipal.domain.responsavel.dto.DetalhesResponsavelDTO;
import br.gov.pe.sjdh.apiIntermunicipal.domain.responsavel.dto.ResponsavelResumoDTO;
import br.gov.pe.sjdh.apiIntermunicipal.domain.responsavel.model.Responsavel;
import br.gov.pe.sjdh.apiIntermunicipal.domain.responsavel.repository.ResponsavelRepository;
import br.gov.pe.sjdh.apiIntermunicipal.infra.exception.BusinessException;
import br.gov.pe.sjdh.apiIntermunicipal.infra.exception.NotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResponsavelService {


    private final ResponsavelRepository repository;

    public Page<ResponsavelResumoDTO> listar(Pageable pageable) {
        return repository.findAll(pageable).map(ResponsavelResumoDTO::new);
    }


    public DetalhesResponsavelDTO detalhar(Integer id) {
        Responsavel r = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Responsável não encontrado"));
        return new DetalhesResponsavelDTO(r);
    }

    public DetalhesResponsavelDTO buscarPorCpf(String cpf) {
        Responsavel r = repository.findByCpf(cpf)
                .orElseThrow(() -> new NotFoundException("Responsável não encontrado"));
        return new DetalhesResponsavelDTO(r);
    }

    @Transactional
    public DetalhesResponsavelDTO cadastrar(DadosCadastrarResponsavelDTO dados) {
        // Regra de negócio simples: impedir CPF duplicado
        repository.findByCpf(dados.cpf()).ifPresent(x -> {
            throw new BusinessException("CPF já cadastrado para outro responsável");
        });

        Responsavel novo = Responsavel.builder()
                .nome(dados.nome())
                .cpf(dados.cpf())
                .rg(dados.rg())
                .ativo(dados.ativo() == null ? true : dados.ativo())
                .build();

        Responsavel salvo = repository.save(novo);
        return new DetalhesResponsavelDTO(salvo);
    }

    @Transactional
    public DetalhesResponsavelDTO atualizar(Integer id, AtualizarResponsavelDTO dados) {
        Responsavel existente = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Responsável não encontrado"));

        if (dados.cpf() != null && !dados.cpf().equals(existente.getCpf())) {
            repository.findByCpf(dados.cpf()).ifPresent(outro -> {
                if (!outro.getId().equals(id)) {
                    throw new BusinessException("CPF já cadastrado para outro responsável");
                }
            });
        }

        if (dados.nome() != null) existente.setNome(dados.nome());
        if (dados.cpf() != null) existente.setCpf(dados.cpf());
        if (dados.rg() != null) existente.setRg(dados.rg());
        if (dados.ativo() != null) existente.setAtivo(dados.ativo());

        Responsavel atualizado = repository.save(existente);
        return new DetalhesResponsavelDTO(atualizado);
    }

    @Transactional
    public void remover(Integer id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("Responsável não encontrado");
        }
        repository.deleteById(id);
    }
}
