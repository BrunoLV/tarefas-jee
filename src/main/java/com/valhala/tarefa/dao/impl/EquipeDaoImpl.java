package com.valhala.tarefa.dao.impl;

import java.util.List;

import javax.persistence.TypedQuery;

import com.valhala.tarefa.dao.api.EquipeDao;
import com.valhala.tarefa.exceptions.ConsultaSemRetornoException;
import com.valhala.tarefa.model.Equipe;

public class EquipeDaoImpl extends BaseDao<Equipe> implements EquipeDao {
	
	public static final Class<Equipe> CLASSE_PERSISTENTE = Equipe.class;
	
	public EquipeDaoImpl() {
		this.classePersistente = EquipeDaoImpl.CLASSE_PERSISTENTE;
	}

	@Override
	public List<Equipe> listarTudo() throws ConsultaSemRetornoException {
		TypedQuery<Equipe> query = this.entityManager.createNamedQuery(Equipe.NAMEDQUERY_BUSCAR_TODOS, EquipeDaoImpl.CLASSE_PERSISTENTE);
		List<Equipe> equipes = query.getResultList();
		if (equipes.isEmpty()) {
			throw new ConsultaSemRetornoException("Consulta não trouxe resultados.");
		} // fim do bloco if
		return equipes;
	}

}
