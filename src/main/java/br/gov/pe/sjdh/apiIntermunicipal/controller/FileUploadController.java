package br.gov.pe.sjdh.apiIntermunicipal.controller;

import br.gov.pe.sjdh.apiIntermunicipal.domain.beneficiario.model.BeneficiarioArquivo;
import br.gov.pe.sjdh.apiIntermunicipal.infra.exception.ErrorResponse;
import br.gov.pe.sjdh.apiIntermunicipal.service.BeneficiarioArquivoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/upload")
@Tag(name = "Upload de Arquivos", description = "Operações de upload de arquivos associados a beneficiários")
public class FileUploadController {

    private final BeneficiarioArquivoService arquivoService;

    public FileUploadController(BeneficiarioArquivoService arquivoService) {
        this.arquivoService = arquivoService;
    }

    @Operation(
            summary = "Enviar arquivo",
            description = "Realiza o upload de um arquivo (multipart/form-data) e associa ao beneficiário e tipo informados.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE)
            )
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Upload realizado com sucesso",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(example = "{\\n  'message': 'Arquivo salvo com sucesso!',\\n  'beneficiarioId': 'a3f2c6f2-1d23-4b6a-9b4e-2e6f1b2c3d4e',\\n  'tipoArquivoId': 2,\\n  'path': 'a3f2c6f2-1d23-4b6a-9b4e-2e6f1b2c3d4e_CPF.pdf'\\n}"))),
            @ApiResponse(responseCode = "400", description = "Requisição inválida (arquivo ausente ou ID inválido)",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "415", description = "Tipo de mídia não suportado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadFile(
        @Parameter(description = "Arquivo a ser enviado", required = true)
        @RequestParam("file") MultipartFile file,
        @Parameter(description = "ID do beneficiário (UUID)", required = true)
        @RequestParam("id") UUID id_beneficiario,
        @Parameter(description = "ID do tipo de arquivo (Short)", required = true)
        @RequestParam("id_tipo_arquivo") Short id_tipo_arquivo
    ) {
        BeneficiarioArquivo registro = arquivoService.uploadAndPersist(id_beneficiario, id_tipo_arquivo, file);

        return ResponseEntity.ok(Map.of(
                "message", "Upload realizado com sucesso",
                "beneficiarioId", id_beneficiario,
                "tipoArquivoId", id_tipo_arquivo,
                "path", registro.getPath(),
                "registroId", registro.getId()
        ));
    }
}
