package com.valhala.tarefa.dao.impl;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.valhala.tarefa.dao.api.Dao;

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
    Class<T> classePersistente;

    // Injeção do EntityManager que será utilizado para as operações com banco de dados.
    @PersistenceContext(name = "tarefas-PU")
    EntityManager entityManager;

    @Override
    public void persistir(T entidade) {
        this.entityManager.persist(entidade);
    } // fim do método persistir

    @Override
    public void atualizar(T entidade) {
        this.entityManager.merge(entidade);
    } // fim do método atualizar

    @Override
    public void remover(T entidade) {
        this.entityManager.remove(entidade);
    } // fim do método remover

    @Override
    public T buscarPorId(Serializable id) {
        T t = this.entityManager.find(this.classePersistente, id);
        return t;
    } // fim do método buscarPorId

} // fim da classe BaseDao
