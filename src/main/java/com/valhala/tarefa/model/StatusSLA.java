package com.valhala.tarefa.model;

/**
 * Enums que representam os status possíveis de tarefas dentro do sistema.
 * @author Bruno Luiz Viana
 * @version 1.0
 */
public enum StatusSLA {

    VIOLADO("Sim"), NAO_VIOLADO("Não"), CONGELADO("Congelado");

    private String descricaoViolacao;

    private StatusSLA(final String descricaoViolacao) {
        this.descricaoViolacao = descricaoViolacao;
    } // fim do metodo construtor

    public String getDescricaoViolacao() {
        return descricaoViolacao;
    }

} // fim do enum StatusSla