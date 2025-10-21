package br.gov.pe.sjdh.apiIntermunicipal.domain.lookup.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * DTO genérico para entidades de lookup/referência.
 * Usado em dropdowns, selects e listas simples.
 *
 * @param id - Identificador da entidade (pode ser Long, Integer ou Short)
 * @param nome - Nome/descrição para exibição
 * @param ativo - status da entidade (opcional, usado apenas quando necessário)
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record DadosLookup(
    Number id,      // Number permite Long, Integer, Short, etc.
    String nome,
    Boolean ativo
) {

    // Construtor sem o campo ativo (para dropdowns simples)
    public DadosLookup(Number id, String nome) {
        this(id, nome, null);
    }

    // Construtor com ativo como boolean primitivo
    public DadosLookup(Number id, String nome, boolean ativo) {
        this(id, nome, Boolean.valueOf(ativo));
    }

    /**
     * Metodo estático para criar instância apenas com id e nome (mais comum)
     */
    public static DadosLookup of(Number id, String nome) {
        return new DadosLookup(id, nome);
    }

    /**
     * Metodo estático para criar instância com status ativo
     */
    public static DadosLookup of(Number id, String nome, boolean ativo) {
        return new DadosLookup(id, nome, ativo);
    }

    // Métodos de conveniência para tipos específicos
    public static DadosLookup of(Long id, String nome) {
        return new DadosLookup(id, nome);
    }

    public static DadosLookup of(Integer id, String nome) {
        return new DadosLookup(id, nome);
    }

    public static DadosLookup of(Short id, String nome) {
        return new DadosLookup(id, nome);
    }
}