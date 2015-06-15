package com.valhala.tarefa.dao.api;

import java.io.Serializable;
import java.util.List;

/**
 * Interface base para todas os DAO's da aplicação.
 * @param <T> representa o tipo genérico definido para a interface.
 * @author Bruno Luiz Viana
 * @version 1.0s
 * @since 23/02/2013
 */
public interface Dao<T> {

    /**
     * Método definido para execução da inserção de um registro no banco de dados.
     * @param t objeto que será inserido no banco de dados.
     */
    void persistir(T t);

    /**
     * Método definido para execução da edição/atualização de um registro no banco de dados.
     * @param t objeto que será editado/atualizado no banco de dados.
     */
    void atualizar(T t);

    /**
     * Método definido para execução da remoção de um registrp no banco de dados.
     * @param t objeto que será removido no banco de dados.
     */
    void remover(T t);

    /**
     * Método determinado para ação de recuperar um registro do banco de dados utilizando a chave primária como
     * paramentro de consulta.
     * @param id representa a chave primaria para realização da consulta.
     * @return objeto retornado do banco de dados.
     */
    T buscarPorId(Serializable id);

    /**
     * Método determinado para ação de recuperar todos os registros do banco de dados.
     * @return lista com os objetos obtidos.
     */
    List<T> listarTudo();

} // fim da interface Dao