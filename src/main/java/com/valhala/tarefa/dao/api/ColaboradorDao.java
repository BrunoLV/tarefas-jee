package com.valhala.tarefa.dao.api;

import com.valhala.tarefa.exceptions.ConsultaSemRetornoException;
import com.valhala.tarefa.model.Colaborador;

/**
 * Interface DAO especializada para a entidade Colaborador.
 * @author Bruno Luiz Viana
 * @version 1.0
 * @since 23/02/2013
 *
 */
public interface ColaboradorDao extends Dao<Colaborador> {
	
	/**
	 * Método utilizado para buscar um colaborador no banco de dados utilizando matricula.
	 * @param matricula
	 * @return
	 * @throws ConsultaSemRetornoException
	 */
	Colaborador buscarPorMatricula(String matricula) throws ConsultaSemRetornoException;
	
	/**
	 * Método utilizado para buscar um colaborador no banco de dados utilizando o nome.
	 * @param nome
	 * @return
	 * @throws ConsultaSemRetornoException
	 */
	Colaborador buscarPorNome(String nome) throws ConsultaSemRetornoException;

} // fim da interface ColaboradorDao