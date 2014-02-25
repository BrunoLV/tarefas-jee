package com.valhala.tarefa.dao.impl;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.valhala.tarefa.dao.api.Dao;
import com.valhala.tarefa.exceptions.ConsultaSemRetornoException;

/**
 * Implementação básica da interface Dao.
 * Implementa os métodos básicos e serve como classe pai das outras DAO implementadas no sistema.
 * @author Bruno Luiz Viana
 * @version 1.0
 * @since 23/02/2014
 *
 * @param <T>
 */
abstract class BaseDao<T> implements Dao<T> {

	protected Class<T> classePersistente;
	
	@PersistenceContext(name="tarefas-PU")
	protected EntityManager entityManager;
	
	@Override
	public void persistir(T entidade) {
		this.entityManager.persist(entidade);
	} // fim do método persistir

	@Override
	public T atualizar(T entidade) {
		return this.entityManager.merge(entidade);
	} // fim do método atualizar

	@Override
	public void remover(T entidade) {
		this.entityManager.remove(entidade);
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
