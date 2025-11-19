package br.gov.pe.sjdh.apiIntermunicipal.controller;

import br.gov.pe.sjdh.apiIntermunicipal.infra.exception.ErrorResponse;
import br.gov.pe.sjdh.apiIntermunicipal.service.BeneficiarioArquivoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/arquivos")
@Tag(name = "Arquivos", description = "Consulta e download de arquivos de beneficiários")
public class ArquivoController {

    private final BeneficiarioArquivoService beneficiarioArquivoService;

    public ArquivoController(BeneficiarioArquivoService beneficiarioArquivoService) {
        this.beneficiarioArquivoService = beneficiarioArquivoService;
    }

    @Operation(
        summary = "Baixar arquivo pelo ID do registro",
        description = "Realiza o download do arquivo associado ao registro informado (tabela beneficiario_arquivo)."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Arquivo retornado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Arquivo não encontrado",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{id}/download")
    public ResponseEntity<byte[]> download(@PathVariable Long id) {
        var payload = beneficiarioArquivoService.carregarArquivoComMeta(id);
        String contentType = payload.contentType();
        if (contentType == null || contentType.isBlank()) {
            contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header("Content-Disposition", "inline; filename=" + payload.filename())
                .body(payload.bytes());
    }
}
