package com.valhala.tarefa.dao.api;

import com.valhala.tarefa.exceptions.ConsultaSemRetornoException;
import com.valhala.tarefa.model.Colaborador;
import com.valhala.tarefa.model.Status;
import com.valhala.tarefa.model.Tarefa;

import java.util.List;

/**
 * Interface DAO especializada para a entidade Tarefa.
 *
 * @author Bruno Luiz Viana
 * @version 1.0
 * @since 23/02/2014
 */
public interface TarefaDao extends Dao<Tarefa> {

    /**
     * Método utilizado para buscar todas as tarefas pertencentes a um colaborador especifico.
     *
     * @param colaborador
     * @return
     * @throws ConsultaSemRetornoException
     */
    List<Tarefa> buscarTodasPorColaborador(Colaborador colaborador) throws ConsultaSemRetornoException;

    /**
     * Método utilizado para buscar todas as tarefas pertencentes a um colaborador especifico e que estejam dentro dentro
     * de algum status passado no método.
     *
     * @param colaborador
     * @param status
     * @return
     * @throws ConsultaSemRetornoException
     */
    List<Tarefa> buscarTodasPorColaboradorEStatus(Colaborador colaborador, List<Status> status) throws ConsultaSemRetornoException;

    /**
     * Método utilizado para buscar todas as tarefas que estejem dentro dos status passados para o método.
     *
     * @param status
     * @return
     * @throws ConsultaSemRetornoException
     */
    List<Tarefa> buscarTodasPorStatus(List<Status> status) throws ConsultaSemRetornoException;

    List<Tarefa> buscarTodasComDatasDefinidades() throws ConsultaSemRetornoException;

    List<Tarefa> buscarTodasPorColaboradorEStatusComDatasDefinidas(Colaborador colaborador, List<Status> status) throws ConsultaSemRetornoException;

    List<Tarefa> buscarTodasPorStatusComDatasDefinidas(List<Status> status) throws ConsultaSemRetornoException;

} // fim da interface TarefaDao