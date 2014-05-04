package com.valhala.tarefa.dao.api;

import com.valhala.tarefa.exceptions.ConsultaSemRetornoException;
import com.valhala.tarefa.model.Equipe;

public interface EquipeDao extends Dao<Equipe> {
	
	Equipe buscarPorNome(String nome) throws ConsultaSemRetornoException;

}
