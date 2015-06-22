package com.valhala.tarefa.dao.jpa;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.valhala.tarefa.dao.api.Dao;

/**
 * Implementação básica da interface Dao. Implementa os métodos básicos e serve
 * como classe pai das outras DAO implementadas no sistema.
 *
 * @param <T>
 * @author Bruno Luiz Viana
 * @version 1.0
 * @since 23/02/2014
 */
abstract class BaseJPADao<E> implements Dao<E> {

	// Propriedade que representa a entidade persistente.
	Class<E> classePersistente;

	// Injeção do EntityManager que será utilizado para as operações com banco de dados.
	@PersistenceContext(name = "tarefas-PU")
	EntityManager entityManager;

	@Override
	public void persistir(E entidade) {
		this.entityManager.persist(entidade);
	} // fim do método persistir

	@Override
	public void atualizar(E entidade) {
		this.entityManager.merge(entidade);
	} // fim do método atualizar

	@Override
	public void remover(E entidade) {
		this.entityManager.remove(entidade);
	} // fim do método remover

	@Override
	public E buscarPorId(Serializable id) {
		E e = this.entityManager.find(this.classePersistente, id);
		return e;
	} // fim do método buscarPorId

	Query construirNamedQuery(String nomeQuery, Object ... parametros) {
		Query query = entityManager.createNamedQuery(nomeQuery);
		if(parametros != null) {
			for (int i = 0; i < parametros.length; i++) {
				query.setParameter(i+1, parametros[i]);
			}
		}
		return query;
	}
	
	TypedQuery<E> construirTypedQuery(String nomeQuery, Object ... parametros) {
    	TypedQuery<E> query = this.entityManager.createNamedQuery(nomeQuery, this.classePersistente);
    	if (parametros != null) {
			for (int i = 0; i < parametros.length; i++) {
				query.setParameter(i+1, parametros[i]);
			}
		}
    	return query;
    }

} // fim da classe BaseDao
