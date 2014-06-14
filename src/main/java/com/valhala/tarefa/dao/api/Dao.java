package com.valhala.tarefa.dao.api;

import com.valhala.tarefa.exceptions.ConsultaSemRetornoException;
import com.valhala.tarefa.exceptions.DaoException;

import java.io.Serializable;
import java.util.List;

/**
 * Interface base para todas os DAO's da aplicação.
 *
 * @param <T>
 * @author Bruno Luiz Viana
 * @version 1.0
 * @since 23/02/2013
 */
public interface Dao<T> {

    /**
     * Método utilizado para persistir uma entidade no banco de dados.
     *
     * @param entidade
     */
    T persistir(T entidade) throws DaoException;

    /**
     * Método utilizado para atualizar uma entidade no banco de dados.
     *
     * @param entidade
     * @return
     */
    T atualizar(T entidade) throws DaoException;

    /**
     * Método utilizado para remover um entidade do banco de dados.
     *
     * @param entidade
     */
    void remover(T entidade) throws DaoException;

    /**
     * Método utilizado para realizar um consulta no banco de dados utilizando o id da entidade.
     *
     * @param id
     * @return
     * @throws ConsultaSemRetornoException
     */
    T buscarPorId(Serializable id) throws ConsultaSemRetornoException;

    /**
     * Método utilizado para buscar todos as entidades de determinado tipo cadastradas no banco de dados.
     *
     * @return
     * @throws ConsultaSemRetornoException
     */
    List<T> listarTudo() throws ConsultaSemRetornoException;

} // fim da interface Dao