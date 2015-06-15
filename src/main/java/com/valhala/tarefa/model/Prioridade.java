package com.valhala.tarefa.model;

/**
 * Enum que representa as possíveis prioridades que podem ser atribuidas a uma tarefa no sistema.
 *
 * @author Bruno Luiz Viana
 * @version 1.0
 * @since 23/02/2014
 */
public enum Prioridade {

    NAO_DEFINIDA("Não Definida"), 
    PLANEJADA("Planejada"), 
    BAIXA("Baixa"), 
    MEDIA("Média"), 
    ALTA("Alta"), 
    CRITICA("Crítica");

    private String nomeExibicao;

    Prioridade(String nomeExibicao) {
        this.nomeExibicao = nomeExibicao;
    }

    public String getNomeExibicao() {
        return this.nomeExibicao;
    }

} // fim da enum Prioridade