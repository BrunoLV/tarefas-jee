package com.valhala.tarefa.model;

/**
 * Enum que define as constantes que representam os possiveis tipos de demanda dentro da aplicação.
 *
 * @author Bruno Luiz Viana
 * @version 1.0
 * @since 23/06/2014
 */
public enum TipoDemanda {

    NAO_DEFINIDO("Não Definida"), ALERTA("Alerta"), CHAMADO("Chamado"), INCIDENTE("Incidente"),
    REQUISICAO_SERVICO("Requisição de Serviço"), DUVIDA_INFORMACAO("Dúvida/Informação"),
    SUGESTAO("Sugestão"), PROBLEMA("Problema"), ZELADORIA("Zeladoria"), PROJETO("Projeto");

    private String nomeExibicao;

    TipoDemanda(String nomeExibicao) {
        this.nomeExibicao = nomeExibicao;
    }

    public String getNomeExibicao() {
        return this.nomeExibicao;
    }

} // fim da enum TipoDemanda
