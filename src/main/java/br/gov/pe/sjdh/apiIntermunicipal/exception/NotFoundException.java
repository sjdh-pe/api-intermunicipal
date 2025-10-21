package br.gov.pe.sjdh.apiIntermunicipal.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}