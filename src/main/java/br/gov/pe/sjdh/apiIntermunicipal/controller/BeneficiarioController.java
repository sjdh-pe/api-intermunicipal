package br.gov.pe.sjdh.apiIntermunicipal.controller;

import br.gov.pe.sjdh.apiIntermunicipal.domain.beneficiario.dto.BeneficiarioCompletoDTO;
import br.gov.pe.sjdh.apiIntermunicipal.domain.beneficiario.dto.CadastrarBeneficiarioDTO;
import br.gov.pe.sjdh.apiIntermunicipal.domain.beneficiario.dto.DetalhesBeneficiarioDTO;
import br.gov.pe.sjdh.apiIntermunicipal.domain.beneficiario.dto.AtualizarBeneficiarioDTO;
import br.gov.pe.sjdh.apiIntermunicipal.infra.exception.BusinessException;
import br.gov.pe.sjdh.apiIntermunicipal.infra.exception.ErrorResponse;
import br.gov.pe.sjdh.apiIntermunicipal.service.BeneficiarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
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

import java.util.UUID;

@RestController
@RequestMapping("/beneficiarios")
@Tag(name = "Beneficiários", description = "Operações relacionadas ao cadastro e consulta de beneficiários")
public class BeneficiarioController {

    @Autowired
    private BeneficiarioService service;

    // ============================================================
    // 🔹 1. Listar beneficiários (paginação)
    // ============================================================
    @Operation(
        summary = "Listar todos os beneficiários",
        description = "Retorna uma lista paginada de beneficiários cadastrados no sistema. Informe os parâmetros de paginação: page, size e sort (p.ex.: sort=updatedAt,desc)."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = BeneficiarioCompletoDTO.class)))
    })
    @GetMapping
    public ResponseEntity<Page<BeneficiarioCompletoDTO>> listar(@ParameterObject Pageable pageable) {
        return ResponseEntity.ok(service.listar(pageable));
    }

    // ============================================================
    // 🔹 3. Buscar beneficiário por CPF e Data de Nascimento
    // ============================================================
    @Operation(
        summary = "Buscar beneficiário por CPF e data de nascimento",
        description = "Retorna os dados completos de um beneficiário com base no CPF e na data de nascimento informados."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Beneficiário encontrado",
            content = @Content(schema = @Schema(implementation = BeneficiarioCompletoDTO.class))),
        @ApiResponse(responseCode = "404", description = "Nenhum beneficiário encontrado com os dados informados",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/cpf/{cpf}/{dataNascimento}")
    public ResponseEntity<BeneficiarioCompletoDTO> buscarPorCpfEDataNascimento(@PathVariable String cpf, @PathVariable String dataNascimento) {
        return ResponseEntity.ok(service.buscarPorCpfEDataNascimento(cpf, dataNascimento));
    }

    // ============================================================
    // 🔹 4. Cadastrar novo beneficiário
    // ============================================================
    @Operation(
        summary = "Cadastrar novo beneficiário",
        description = "Cadastra um novo beneficiário no sistema com base nos dados fornecidos no corpo da requisição."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Beneficiário cadastrado com sucesso",
            content = @Content(schema = @Schema(implementation = DetalhesBeneficiarioDTO.class))),
        @ApiResponse(responseCode = "400", description = "Erro de validação dos campos",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping
    public ResponseEntity<DetalhesBeneficiarioDTO> cadastrarBeneficiario(
            @RequestBody(description = "Dados do beneficiário a cadastrar", required = true)
            @org.springframework.web.bind.annotation.RequestBody @Valid CadastrarBeneficiarioDTO dados){

        return service.cadastrar(dados);

    }

    // ============================================================
    // 🔹 5. Atualizar beneficiário existente (PUT)
    // ============================================================
    @Operation(
        summary = "Atualizar beneficiário",
        description = "Atualiza dados de um beneficiário existente. Informe o UUID na URL e o corpo com os campos a alterar."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Beneficiário atualizado com sucesso",
            content = @Content(schema = @Schema(implementation = DetalhesBeneficiarioDTO.class))),
        @ApiResponse(responseCode = "400", description = "Erro de validação",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "404", description = "Beneficiário não encontrado",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<DetalhesBeneficiarioDTO> atualizarBeneficiario(
            @PathVariable UUID id,
            @RequestBody(description = "Dados para atualização do beneficiário", required = true)
            @org.springframework.web.bind.annotation.RequestBody @Valid AtualizarBeneficiarioDTO dados) {

        // Garante que o ID do path prevalece sobre o corpo
        if (dados.id() != null && !dados.id().equals(id)) {
            throw new BusinessException("ID do corpo da requisição difere do ID da URL");
        }
        var resposta = service.atualizar(id, dados);
        return ResponseEntity.ok(resposta);
    }

    // ============================================================
    // 🔹 6. Detalhar beneficiário por ID
    // ============================================================
    @Operation(
        summary = "Buscar beneficiário por ID",
        description = "Obtém os dados completos de um beneficiário pelo seu identificador único (UUID)."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Beneficiário encontrado",
            content = @Content(schema = @Schema(implementation = BeneficiarioCompletoDTO.class))),
        @ApiResponse(responseCode = "404", description = "Beneficiário não encontrado",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<BeneficiarioCompletoDTO> detalhar(@PathVariable UUID id) {
        return ResponseEntity.ok(service.detalhar(id));
    }
}
