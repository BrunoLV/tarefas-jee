package com.valhala.tarefa.model;

/**
 * Enum que representa os status possiveis de uma tarefa dentro do sistema.
 *
 * @author Bruno Luiz Viana
 * @version 1.0
 * @since 23/02/2014
 */
public enum Status {

    ABERTO("Aberto"),
    AGUARDANDO("Aguardando"),
    AGUARDANDO_CLIENTE("Aguardando cliente"),
    AGUARDANDO_FORNECEDOR("Aguardando fornecedor"),
    AGUARDANDO_INFORMACAO_DO_CLIENTE("Aguardando informação do cliente"),
    AGUARDANDO_RDM("Aguardando RDM"),
    EM_ATENDIMENTO("Em atendimento"),
    EM_ANALISE("Em analise"),
    EM_APROVACAO("Em aprovação"),
    EM_APROVACAO_TECNICA("Em aprovação técnica"),
    EM_COTACAO("Em cotação"),
    EM_HOMOLOGACAO("Em homologação"),
    EM_HOMOLOGACAO_PELO_CLIENTE("Em homologação pelo cliente"),
    EM_PLANEJAMENTO("Em planejamento"),
    HOMOLOGADO("Homologado"),
    PLANEJADO("Planejado"),
    TRIAGEM("Triagem"),
    CONCLUIDO("Concluído");

    private String nomeExibicao;

    Status(String nomeExibicao) {
        this.nomeExibicao = nomeExibicao;
    } // fim do construtor

    public String getNomeExibicao() {
        return this.nomeExibicao;
    }

} // fim do enum Status
