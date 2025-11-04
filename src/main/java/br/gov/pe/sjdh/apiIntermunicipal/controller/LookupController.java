package br.gov.pe.sjdh.apiIntermunicipal.controller;


import br.gov.pe.sjdh.apiIntermunicipal.domain.lookup.cidade.Cidade;
import br.gov.pe.sjdh.apiIntermunicipal.domain.lookup.etnia.Etnia;
import br.gov.pe.sjdh.apiIntermunicipal.domain.lookup.localRetirada.LocalRetirada;
import br.gov.pe.sjdh.apiIntermunicipal.domain.lookup.sexoBeneficiario.SexoBeneficiario;
import br.gov.pe.sjdh.apiIntermunicipal.domain.lookup.statusBeneficio.StatusBeneficiario;
import br.gov.pe.sjdh.apiIntermunicipal.domain.lookup.tipoArquivo.TipoArquivo;
import br.gov.pe.sjdh.apiIntermunicipal.domain.lookup.tipoDeficiencia.TipoDeficiencia;
import br.gov.pe.sjdh.apiIntermunicipal.infra.exception.ErrorResponse;
import br.gov.pe.sjdh.apiIntermunicipal.service.LookupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/lookup")
@Tag(name = "Lookups", description = "Consultas de dados de referência (cidades, etnias, sexos, status de benefício, locais de retirada, tipos de deficiência, tipos de arquivo)")
public class LookupController {

    private final LookupService lookupService;

    public LookupController(LookupService lookupService) {
        this.lookupService = lookupService;
    }

    @Operation(summary = "Listar etnias", description = "Retorna a lista de etnias disponíveis para seleção.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso",
                    content = @Content(schema = @Schema(implementation = Etnia.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/etnias")
    public List<Etnia> listarEtnias() {
        return lookupService.listarEtnias();
    }

    @Operation(summary = "Listar cidades", description = "Retorna a lista de cidades cadastradas.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso",
                    content = @Content(schema = @Schema(implementation = Cidade.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/cidades")
    public List<Cidade> listarCidades() {
        return lookupService.listarCidades();
    }

    @Operation(summary = "Listar tipos de deficiência", description = "Retorna a lista de tipos de deficiência suportados.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso",
                    content = @Content(schema = @Schema(implementation = TipoDeficiencia.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/tipos-deficiencia")
    public List<TipoDeficiencia> listarTiposDeficiencia() {
        return lookupService.listarTiposDeficiencia();
    }

    @Operation(summary = "Listar sexos", description = "Retorna a lista de sexos do beneficiário.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso",
                    content = @Content(schema = @Schema(implementation = SexoBeneficiario.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/sexos")
    public List<SexoBeneficiario> listarSexos() {
        return lookupService.listarSexos();
    }

    @Operation(summary = "Listar status do benefício", description = "Retorna a lista de status possíveis do benefício.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso",
                    content = @Content(schema = @Schema(implementation = StatusBeneficiario.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/status-beneficio")
    public List<StatusBeneficiario> listarStatusBeneficio() {
        return lookupService.listarStatusBeneficio();
    }

    @Operation(summary = "Listar locais de retirada", description = "Retorna a lista de locais de retirada cadastrados.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso",
                    content = @Content(schema = @Schema(implementation = LocalRetirada.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/locais-retirada")
    public List<LocalRetirada> listarLocaisRetirada() {
        return lookupService.listarLocaisRetirada();
    }

    @Operation(summary = "Listar tipos de arquivo", description = "Retorna a lista de tipos de arquivo aceitos.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso",
                    content = @Content(schema = @Schema(implementation = TipoArquivo.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/tipos-arquivo")
    public List<TipoArquivo> listarTiposArquivo() {
        return lookupService.listarTiposArquivo();
    }
}
