package br.gov.pe.sjdh.apiIntermunicipal.controller;

import br.gov.pe.sjdh.apiIntermunicipal.domain.beneficiario.dto.BeneficiarioCompletoDTO;
import br.gov.pe.sjdh.apiIntermunicipal.domain.beneficiario.dto.DadosCadastrarBeneficiarioDTO;
import br.gov.pe.sjdh.apiIntermunicipal.domain.beneficiario.dto.DetalhesBeneficiarioDTO;
import br.gov.pe.sjdh.apiIntermunicipal.exception.ErrorResponse;
import br.gov.pe.sjdh.apiIntermunicipal.service.BeneficiarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    // 🔹 1. Buscar beneficiário por CPF e Data de Nascimento
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
    // 🔹 2. Listar beneficiários (paginação)
    // ============================================================
    @Operation(
        summary = "Listar todos os beneficiários",
        description = "Retorna uma lista paginada de beneficiários cadastrados no sistema."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso",
            content = @Content(schema = @Schema(implementation = BeneficiarioCompletoDTO.class))),
        @ApiResponse(responseCode = "204", description = "Nenhum beneficiário encontrado")
    })
    @GetMapping
    public ResponseEntity<Page<BeneficiarioCompletoDTO>> listar(Pageable pageable) {
        return ResponseEntity.ok(service.listar(pageable));
    }



    // ============================================================
    // 🔹 3. Detalhar beneficiário por ID
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
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @PostMapping
    public ResponseEntity<DetalhesBeneficiarioDTO> cadastrarBeneficiario(
            @RequestBody DadosCadastrarBeneficiarioDTO dados){

        return service.cadastrar(dados);

    }



    @GetMapping("/hello")
    public String hello() {
        return "API Intermunicipal ativa!";
    }

    @PostMapping("/test-post/{dados}")
    public String testPost(@PathVariable String dados) {
        System.out.printf("Recebido via POST: %s\n", dados);
        return "POST recebido!";
    }

}
