package com.valhala.tarefa.model;

/**
 * Created by Bruno Luiz Viana on 06/06/2014.
 */
public enum TipoDemanda {

    NAO_DEFINIDO("Não Definida"),
    ALERTA("Alerta"),
    CHAMADO("Chamado"),
    INCIDENTE("Incidente"),
    REQUISICAO_SERVICO("Requisição de Serviço"),
    DUVIDA_INFORMACAO("Dúvida/Informação"),
    SUGESTAO("Sugestão"),
    PROBLEMA("Problema"),
    ZELADORIA("Zeladoria"),
    PROJETO("Projeto");

    private String nomeExibicao;

    TipoDemanda(String nomeExibicao) {
        this.nomeExibicao = nomeExibicao;
    }

    public String getNomeExibicao() {
        return this.nomeExibicao;
    }

}
