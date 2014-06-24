package com.valhala.tarefa.model;

/**
 * Enum que representa os status possiveis de uma tarefa dentro do sistema.
 *
 * @author Bruno Luiz Viana
 * @version 1.0
 * @since 23/02/2014
 */
public enum Status {

    ABERTO("Aberto"), EM_ATENDIMENTO("Em Atendimento"), AGUARDANDO("Aguardando"), AGUARDANDO_FORNECEDORES("Aguardando Fornecedores"),
    AGUARDANDO_INFORMACAO_DO_CLIENTE("Aguardando Informação do Cliente"), AGUARDANDO_RDM("Aguardando RDM"),
    EM_APROVACAO_TECNICA("Em Aprovação Técnica"), EM_COTACAO("Em cotação"), EM_HOMOLOGACAO("Em Homologação"), EM_HOMOLOGACAO_PELO_CLIENTE("Em Homologação pelo Cliente"),
    EM_PLANEJAMENTO("Em Planejamento"), HOMOLOGADO("Homologado"), PLANEJADO("Planejado"), TRIAGEM("Triagem"), CONCLUIDO("Concluído");

    private String nomeExibicao;

    Status(String nomeExibicao) {
        this.nomeExibicao = nomeExibicao;
    } // fim do construtor

    public String getNomeExibicao() {
        return this.nomeExibicao;
    }

} // fim do enum Status