package com.valhala.tarefa.dao.api;

import com.valhala.tarefa.exceptions.ConsultaSemRetornoException;
import com.valhala.tarefa.model.Equipe;

/**
 * Interface DAO especializada na entidade Equipe.
 *
 * @author Bruno Luiz Viana
 * @version 1.0
 * @since 04/06/2014
 */
public interface EquipeDao extends Dao<Equipe> {

    /**
     * Método utilizado para buscar uma equipe pelo nome.
     *
     * @param nome
     * @return
     * @throws ConsultaSemRetornoException
     */
    Equipe buscarPorNome(String nome);

} // fim da interface EquipeDao
