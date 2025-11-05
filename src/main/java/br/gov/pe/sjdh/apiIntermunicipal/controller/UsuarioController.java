package br.gov.pe.sjdh.apiIntermunicipal.controller;

import br.gov.pe.sjdh.apiIntermunicipal.domain.usuario.dto.AtualizarUsuarioDTO;
import br.gov.pe.sjdh.apiIntermunicipal.domain.usuario.dto.DadosCadastrarUsuarioDTO;
import br.gov.pe.sjdh.apiIntermunicipal.domain.usuario.dto.DetalhesUsuarioDTO;
import br.gov.pe.sjdh.apiIntermunicipal.domain.usuario.dto.UsuarioResumoDTO;
import br.gov.pe.sjdh.apiIntermunicipal.infra.exception.ErrorResponse;
import br.gov.pe.sjdh.apiIntermunicipal.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
@Tag(name = "Usuários", description = "Operações relacionadas ao cadastro e consulta de usuários")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    // ============================================================
    // 1. Listar usuários (paginação)
    // ============================================================
    @Operation(summary = "Listar usuários", description = "Retorna uma lista paginada de usuários cadastrados. Informe os parâmetros de paginação: page, size e sort (p.ex.: sort=updatedAt,desc).")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = UsuarioResumoDTO.class)))
    })
    @GetMapping
    public ResponseEntity<Page<UsuarioResumoDTO>> listar(@ParameterObject Pageable pageable) {
        return ResponseEntity.ok(service.listar(pageable));
    }

    // ============================================================
    // 2. Buscar usuário por ID
    // ============================================================
    @Operation(summary = "Buscar usuário por ID", description = "Obtém os dados de um usuário pelo seu identificador (Integer).")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuário encontrado",
                    content = @Content(schema = @Schema(implementation = DetalhesUsuarioDTO.class))),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<DetalhesUsuarioDTO> detalhar(@PathVariable Integer id) {
        return ResponseEntity.ok(service.detalhar(id));
    }

    // ============================================================
    // 3. Buscar usuário por CPF
    // ============================================================
    @Operation(summary = "Buscar usuário por CPF", description = "Obtém os dados de um usuário por CPF (somente dígitos).")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuário encontrado",
                    content = @Content(schema = @Schema(implementation = DetalhesUsuarioDTO.class))),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<DetalhesUsuarioDTO> buscarPorCpf(@PathVariable String cpf) {
        return ResponseEntity.ok(service.buscarPorCpf(cpf));
    }

    // ============================================================
    // 4. Buscar usuário por E-mail
    // ============================================================
    @Operation(summary = "Buscar usuário por e-mail", description = "Obtém os dados de um usuário por e-mail.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuário encontrado",
                    content = @Content(schema = @Schema(implementation = DetalhesUsuarioDTO.class))),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/email/{email}")
    public ResponseEntity<DetalhesUsuarioDTO> buscarPorEmail(@PathVariable String email) {
        return ResponseEntity.ok(service.buscarPorEmail(email));
    }

    // ============================================================
    // 5. Cadastrar usuário
    // ============================================================
    @Operation(summary = "Cadastrar usuário", description = "Cadastra um novo usuário.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuário cadastrado",
                    content = @Content(schema = @Schema(implementation = DetalhesUsuarioDTO.class))),
            @ApiResponse(responseCode = "400", description = "Erro de validação",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping
    public ResponseEntity<DetalhesUsuarioDTO> cadastrar(@RequestBody @Valid DadosCadastrarUsuarioDTO dados) {
        return ResponseEntity.status(201).body(service.cadastrar(dados));
    }

    // ============================================================
    // 6. Atualizar usuário
    // ============================================================
    @Operation(summary = "Atualizar usuário", description = "Atualiza os dados de um usuário existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuário atualizado",
                    content = @Content(schema = @Schema(implementation = DetalhesUsuarioDTO.class))),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<DetalhesUsuarioDTO> atualizar(@PathVariable Integer id, @RequestBody @Valid AtualizarUsuarioDTO dados) {
        return ResponseEntity.ok(service.atualizar(id, dados));
    }

    // ============================================================
    // 7. Remover usuário
    // ============================================================
    @Operation(summary = "Remover usuário", description = "Remove um usuário pelo ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Usuário removido"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Integer id) {
        service.remover(id);
        return ResponseEntity.noContent().build();
    }
}
