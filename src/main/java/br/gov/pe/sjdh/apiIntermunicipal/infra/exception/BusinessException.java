package br.gov.pe.sjdh.apiIntermunicipal.infra.exception;

public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}
