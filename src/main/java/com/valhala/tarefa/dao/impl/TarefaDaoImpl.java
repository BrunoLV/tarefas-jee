package com.valhala.tarefa.dao.impl;

import com.valhala.tarefa.dao.api.TarefaDao;
import com.valhala.tarefa.exceptions.ConsultaSemRetornoException;
import com.valhala.tarefa.model.Colaborador;
import com.valhala.tarefa.model.Status;
import com.valhala.tarefa.model.Tarefa;

import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Classe que implementa TarefaDao
 *
 * @author Bruno Luiz Viana
 * @version 1.0
 * @since 23/02/2014
 */
public class TarefaDaoImpl extends BaseDao<Tarefa> implements TarefaDao {

    public static final Class<Tarefa> CLASSE_PERSISTENTE = Tarefa.class;

    public TarefaDaoImpl() {
        this.classePersistente = TarefaDaoImpl.CLASSE_PERSISTENTE;
    } // fim do método construtor

    @Override
    public List<Tarefa> listarTudo() throws ConsultaSemRetornoException {
        TypedQuery<Tarefa> query = this.entityManager.createNamedQuery(Tarefa.NAMEDQUERY_BUSCAR_TODOS, TarefaDaoImpl.CLASSE_PERSISTENTE);
        List<Tarefa> tarefas = query.getResultList();
        if (tarefas.isEmpty()) {
            throw new ConsultaSemRetornoException("Consulta não trouxe resultados.");
        } // fim do bloco if
        return tarefas;
    } // fim do método listarTudo

    @Override
    public List<Tarefa> buscarTodasPorColaborador(Colaborador colaborador) throws ConsultaSemRetornoException {
        TypedQuery<Tarefa> query = this.entityManager.createNamedQuery(Tarefa.NAMEDQUERY_BUSCAR_POR_COLABORADOR, TarefaDaoImpl.CLASSE_PERSISTENTE);
        query.setParameter("id", colaborador);
        List<Tarefa> tarefas = query.getResultList();
        if (tarefas.isEmpty()) {
            throw new ConsultaSemRetornoException("Consulta não trouxe resultados.");
        } // fim do bloco if
        return tarefas;
    } // fim do método buscarTodasPorIdColaborador

    @Override
    public List<Tarefa> buscarTodasPorColaboradorEStatus(Colaborador colaborador, List<Status> status) throws ConsultaSemRetornoException {
        TypedQuery<Tarefa> query = this.entityManager.createNamedQuery(Tarefa.NAMEDQUERY_BUSCAR_POR_COLABORADOR_E_STATUS, TarefaDaoImpl.CLASSE_PERSISTENTE);
        query.setParameter("colaborador", colaborador);
        query.setParameter("status", status);
        List<Tarefa> tarefas = query.getResultList();
        if (tarefas.isEmpty()) {
            throw new ConsultaSemRetornoException("Consulta não trouxe resultados.");
        } // fim do bloco if
        return tarefas;
    } // fim do método buscarTodasPorColaboradorEStatus

    @Override
    public List<Tarefa> buscarTodasPorStatus(List<Status> status) throws ConsultaSemRetornoException {
        TypedQuery<Tarefa> query = this.entityManager.createNamedQuery(Tarefa.NAMEDQUERY_BUSCAR_POR_STATUS, TarefaDaoImpl.CLASSE_PERSISTENTE);
        query.setParameter("status", status);
        List<Tarefa> tarefas = query.getResultList();
        if (tarefas.isEmpty()) {
            throw new ConsultaSemRetornoException("Consulta não trouxe resultados.");
        } // fim do bloco if
        return tarefas;
    } // fim do método buscarTodasTarefasPorStatus

    @Override
    public List<Tarefa> buscarTodasComDatasDefinidas() throws ConsultaSemRetornoException {
        TypedQuery<Tarefa> query = this.entityManager.createNamedQuery(Tarefa.NAMEDQUERY_BUSCAR_TODOS_DATAS_DEFINIDAS, TarefaDaoImpl.CLASSE_PERSISTENTE);
        List<Tarefa> tarefas = query.getResultList();
        if (tarefas.isEmpty()) {
            throw new ConsultaSemRetornoException("Consulta não trouxe resultados.");
        } // fim do bloco if
        return tarefas;
    } // fim do método buscarTodasPorStatusComDatasDefinidas

    @Override
    public List<Tarefa> buscarTodasPorColaboradorEStatusComDatasDefinidas(Colaborador colaborador, List<Status> status) throws ConsultaSemRetornoException {
        TypedQuery<Tarefa> query = this.entityManager.createNamedQuery(Tarefa.NAMEDQUERY_BUSCAR_POR_COLABORADOR_E_STATUS_DATAS_DEFINIDAS, TarefaDaoImpl.CLASSE_PERSISTENTE);
        query.setParameter("colaborador", colaborador);
        query.setParameter("status", status);
        List<Tarefa> tarefas = query.getResultList();
        if (tarefas.isEmpty()) {
            throw new ConsultaSemRetornoException("Consulta não trouxe resultados.");
        } // fim do bloco if
        return tarefas;
    } // fim do método buscarTodasPorColaboradorEStatusComDatasDefinidas

    @Override
    public List<Tarefa> buscarTodasPorStatusComDatasDefinidas(List<Status> status) throws ConsultaSemRetornoException {
        TypedQuery<Tarefa> query = this.entityManager.createNamedQuery(Tarefa.NAMEDQUERY_BUSCAR_POR_STATUS_DATAS_DEFINIDAS, TarefaDaoImpl.CLASSE_PERSISTENTE);
        query.setParameter("status", status);
        List<Tarefa> tarefas = query.getResultList();
        if (tarefas.isEmpty()) {
            throw new ConsultaSemRetornoException("Consulta não trouxe resultados.");
        } // fim do bloco if
        return tarefas;
    } // fim do método buscarTodasPorStatusComDatasDefinidas

} // fim da classe TarefaDaoImpl