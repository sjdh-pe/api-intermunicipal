package br.gov.pe.sjdh.apiIntermunicipal.service;

import br.gov.pe.sjdh.apiIntermunicipal.domain.usuario.dto.AtualizarUsuarioDTO;
import br.gov.pe.sjdh.apiIntermunicipal.domain.usuario.dto.DadosCadastrarUsuarioDTO;
import br.gov.pe.sjdh.apiIntermunicipal.domain.usuario.dto.DetalhesUsuarioDTO;
import br.gov.pe.sjdh.apiIntermunicipal.domain.usuario.dto.UsuarioResumoDTO;
import br.gov.pe.sjdh.apiIntermunicipal.domain.usuario.model.Usuario;
import br.gov.pe.sjdh.apiIntermunicipal.domain.usuario.repository.UsuarioRepository;
import br.gov.pe.sjdh.apiIntermunicipal.infra.exception.BusinessException;
import br.gov.pe.sjdh.apiIntermunicipal.infra.exception.NotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository repository;

    public Page<UsuarioResumoDTO> listar(Pageable pageable) {
        return repository.findAll(pageable).map(UsuarioResumoDTO::new);
    }

    public DetalhesUsuarioDTO detalhar(Integer id) {
        Usuario u = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
        return new DetalhesUsuarioDTO(u);
    }

    public DetalhesUsuarioDTO buscarPorCpf(String cpf) {
        Usuario u = repository.findByCpf(cpf)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
        return new DetalhesUsuarioDTO(u);
    }

    public DetalhesUsuarioDTO buscarPorEmail(String email) {
        Usuario u = repository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
        return new DetalhesUsuarioDTO(u);
    }

    @Transactional
    public DetalhesUsuarioDTO cadastrar(DadosCadastrarUsuarioDTO dados) {
        // Regra de negócio simples: impedir CPF duplicado
        repository.findByCpf(dados.cpf()).ifPresent(x -> {
            throw new BusinessException("CPF já cadastrado para outro usuário");
        });
        repository.findByEmail(dados.email()).ifPresent(x -> {
            throw new BusinessException("E-mail já cadastrado para outro usuário");
        });

        Usuario novo = Usuario.builder()
                .nome(dados.nome())
                .cpf(dados.cpf())
                .senha(dados.senha())
                .email(dados.email())
                .ativo(dados.ativo() == null ? true : dados.ativo())
                .build();

        Usuario salvo = repository.save(novo);
        return new DetalhesUsuarioDTO(salvo);
    }

    @Transactional
    public DetalhesUsuarioDTO atualizar(Integer id, AtualizarUsuarioDTO dados) {
        Usuario existente = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));

        if (dados.cpf() != null && !dados.cpf().equals(existente.getCpf())) {
            repository.findByCpf(dados.cpf()).ifPresent(outro -> {
                if (!outro.getId().equals(id)) {
                    throw new BusinessException("CPF já cadastrado para outro usuário");
                }
            });
        }
        if (dados.email() != null && !dados.email().equalsIgnoreCase(existente.getEmail())) {
            repository.findByEmail(dados.email()).ifPresent(outro -> {
                if (!outro.getId().equals(id)) {
                    throw new BusinessException("E-mail já cadastrado para outro usuário");
                }
            });
        }

        if (dados.nome() != null) existente.setNome(dados.nome());
        if (dados.cpf() != null) existente.setCpf(dados.cpf());
        if (dados.senha() != null) existente.setSenha(dados.senha());
        if (dados.email() != null) existente.setEmail(dados.email());
        if (dados.ativo() != null) existente.setAtivo(dados.ativo());

        Usuario atualizado = repository.save(existente);
        return new DetalhesUsuarioDTO(atualizado);
    }

    @Transactional
    public void remover(Integer id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("Usuário não encontrado");
        }
        repository.deleteById(id);
    }
}
