package br.gov.pe.sjdh.apiIntermunicipal.infra.exception;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

/*
 * Classe para retornar erros da API
 */
@Getter
@Schema(description = "Estrutura padrão de retorno de erros da API")
public class ErrorResponse {

    @Schema(description = "Momento em que o erro ocorreu", example = "2025-10-08T09:35:48.521")
    private final  LocalDateTime timestamp;

    @Schema(description = "Código de status HTTP do erro", example = "404")
    private final int status;

    @Schema(description = "Mensagem de erro principal", example = "Beneficiário não encontrado")
    private final String error;

    @Schema(description = "Lista de detalhes adicionais do erro (opcional)")
    private final List<String> details;

    public ErrorResponse(LocalDateTime timestamp, int status, String error, List<String> details) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.details = details;
    }

}