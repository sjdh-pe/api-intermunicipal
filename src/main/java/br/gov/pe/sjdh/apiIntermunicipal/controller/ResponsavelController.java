package br.gov.pe.sjdh.apiIntermunicipal.controller;

import br.gov.pe.sjdh.apiIntermunicipal.domain.responsavel.dto.AtualizarResponsavelDTO;
import br.gov.pe.sjdh.apiIntermunicipal.domain.responsavel.dto.DadosCadastrarResponsavelDTO;
import br.gov.pe.sjdh.apiIntermunicipal.domain.responsavel.dto.DetalhesResponsavelDTO;
import br.gov.pe.sjdh.apiIntermunicipal.domain.responsavel.dto.ResponsavelResumoDTO;
import br.gov.pe.sjdh.apiIntermunicipal.infra.exception.ErrorResponse;
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
import br.gov.pe.sjdh.apiIntermunicipal.service.ResponsavelService;

@RestController
@RequestMapping("/responsaveis")
@Tag(name = "Responsáveis", description = "Operações relacionadas ao cadastro e consulta de responsáveis")
public class ResponsavelController {

    @Autowired
    private ResponsavelService service;

    // ============================================================
    // 1. Listar responsáveis (paginação)
    // ============================================================
    @Operation(summary = "Listar responsáveis", description = "Retorna uma lista paginada de responsáveis cadastrados. Informe os parâmetros de paginação: page, size e sort (p.ex.: sort=updatedAt,desc).")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponsavelResumoDTO.class)))
    })
    @GetMapping
    public ResponseEntity<Page<ResponsavelResumoDTO>> listar(@ParameterObject Pageable pageable) {
        return ResponseEntity.ok(service.listar(pageable));
    }

    // ============================================================
    // 2. Buscar responsável por ID
    // ============================================================
    @Operation(summary = "Buscar responsável por ID", description = "Obtém os dados de um responsável pelo seu identificador (Integer).")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Responsável encontrado",
                    content = @Content(schema = @Schema(implementation = DetalhesResponsavelDTO.class))),
            @ApiResponse(responseCode = "404", description = "Responsável não encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<DetalhesResponsavelDTO> detalhar(@PathVariable Integer id) {
        return ResponseEntity.ok(service.detalhar(id));
    }

    // ============================================================
    // 3. Buscar responsável por CPF
    // ============================================================
    @Operation(summary = "Buscar responsável por CPF", description = "Obtém os dados de um responsável por CPF (somente dígitos).")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Responsável encontrado",
                    content = @Content(schema = @Schema(implementation = DetalhesResponsavelDTO.class))),
            @ApiResponse(responseCode = "404", description = "Responsável não encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<DetalhesResponsavelDTO> buscarPorCpf(@PathVariable String cpf) {
        return ResponseEntity.ok(service.buscarPorCpf(cpf));
    }

    // ============================================================
    // 4. Cadastrar responsável
    // ============================================================
    @Operation(summary = "Cadastrar responsável", description = "Cadastra um novo responsável.")
    @ApiResponses({
                @ApiResponse(responseCode = "201", description = "Responsável cadastrado",
                        content = @Content(schema = @Schema(implementation = DetalhesResponsavelDTO.class))),
                @ApiResponse(responseCode = "400", description = "Erro de validação",
                        content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
        })
    @PostMapping
    public ResponseEntity<DetalhesResponsavelDTO> cadastrar(@RequestBody @Valid DadosCadastrarResponsavelDTO dados) {
        return ResponseEntity.status(201).body(service.cadastrar(dados));
    }

    // ============================================================
    // 5. Atualizar responsável
    // ============================================================
    @Operation(summary = "Atualizar responsável", description = "Atualiza os dados de um responsável existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Responsável atualizado",
                    content = @Content(schema = @Schema(implementation = DetalhesResponsavelDTO.class))),
            @ApiResponse(responseCode = "404", description = "Responsável não encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<DetalhesResponsavelDTO> atualizar(@PathVariable Integer id, @RequestBody @Valid AtualizarResponsavelDTO dados) {
        return ResponseEntity.ok(service.atualizar(id, dados));
    }

    // ============================================================
    // 6. Remover responsável
    // ============================================================
    @Operation(summary = "Remover responsável", description = "Remove um responsável pelo ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Responsável removido"),
            @ApiResponse(responseCode = "404", description = "Responsável não encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Integer id) {
        service.remover(id);
        return ResponseEntity.noContent().build();
    }
}
