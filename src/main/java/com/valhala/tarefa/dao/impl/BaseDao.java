package com.valhala.tarefa.dao.impl;

import com.valhala.tarefa.dao.api.Dao;
import com.valhala.tarefa.exceptions.ConsultaSemRetornoException;
import com.valhala.tarefa.exceptions.DaoException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.io.Serializable;

/**
 * Implementação básica da interface Dao.
 * Implementa os métodos básicos e serve como classe pai das outras DAO implementadas no sistema.
 *
 * @param <T>
 * @author Bruno Luiz Viana
 * @version 1.0
 * @since 23/02/2014
 */
abstract class BaseDao<T> implements Dao<T> {

    // Propriedade que representa a entidade persistente.
    protected Class<T> classePersistente;

    // Injeção do EntityManager que será utilizado para as operações com banco de dados.
    @PersistenceContext(name = "tarefas-PU")
    protected EntityManager entityManager;

    @Override
    public T persistir(T entidade) throws DaoException {
        try {
            this.entityManager.persist(entidade);
        } catch (PersistenceException e) {
            throw new DaoException(String.format("Ocorreu um erro ao persistir o registro. Erro: %s", e.getMessage()), e);
        } // fim do bloco try/catch
        return entidade;
    } // fim do método persistir

    @Override
    public T atualizar(T entidade) throws DaoException {
        try {
            entidade = this.entityManager.merge(entidade);
        } catch (PersistenceException e) {
            throw new DaoException(String.format("Ocorreu um erro ao atualizar o registro. Erro: %s", e.getMessage()), e);
        } // fim do bloco try/catch
        return entidade;
    } // fim do método atualizar

    @Override
    public void remover(T entidade) throws DaoException {
        try {
            this.entityManager.remove(entidade);
        } catch (PersistenceException e) {
            throw new DaoException(String.format("Ocorreu um erro ao atualizar o registro. Erro: %s", e.getMessage()), e);
        } // fim do bloco try/catch
    } // fim do método remover

    @Override
    public T buscarPorId(Serializable id) throws ConsultaSemRetornoException {
        T t = this.entityManager.find(this.classePersistente, id);
        if (t == null) {
            throw new ConsultaSemRetornoException("Consulta não trouxe resultados.");
        } // fim do bloco if
        return t;
    } // fim do método buscarPorId

} // fim da classe BaseDao
