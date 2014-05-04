package com.valhala.tarefa.dao.impl;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import com.valhala.tarefa.dao.api.EquipeDao;
import com.valhala.tarefa.exceptions.ConsultaSemRetornoException;
import com.valhala.tarefa.model.Equipe;

public class EquipeDaoImpl extends BaseDao<Equipe> implements EquipeDao {
	
	public static final Class<Equipe> CLASSE_PERSISTENTE = Equipe.class;
	
	public EquipeDaoImpl() {
		this.classePersistente = EquipeDaoImpl.CLASSE_PERSISTENTE;
	} // fim do método construtor

	@Override
	public List<Equipe> listarTudo() throws ConsultaSemRetornoException {
		TypedQuery<Equipe> query = this.entityManager.createNamedQuery(Equipe.NAMEDQUERY_BUSCAR_TODOS, EquipeDaoImpl.CLASSE_PERSISTENTE);
		List<Equipe> equipes = query.getResultList();
		if (equipes.isEmpty()) {
			throw new ConsultaSemRetornoException("Consulta não trouxe resultados.");
		} // fim do bloco if
		return equipes;
	} // fim do método listarTudo

	@Override
	public Equipe buscarPorNome(String nome) throws ConsultaSemRetornoException {
		TypedQuery<Equipe> query = this.entityManager.createNamedQuery(Equipe.NAMEDQUERY_BUSCAR_POR_NOME, EquipeDaoImpl.CLASSE_PERSISTENTE);
		query.setParameter("nome", nome);
		Equipe equipe;
		try {
			equipe = query.getSingleResult();
		} catch (NoResultException e) {
			throw new ConsultaSemRetornoException("Consulta não trouxe resultados.");
		} // fim do bloco try/catch
		return equipe;
	} // fim do método buscarPorNome

} // fim da classe EquipeDaoImpl