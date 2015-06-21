package com.valhala.tarefa.dao.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.valhala.tarefa.dao.api.TarefaDao;
import com.valhala.tarefa.model.Colaborador;
import com.valhala.tarefa.model.Status;
import com.valhala.tarefa.model.Tarefa;

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
    public List<Tarefa> listarTudo() {
        TypedQuery<Tarefa> query = this.entityManager.createNamedQuery(Tarefa.NAMED_QUERY_BUSCAR_TODOS, TarefaDaoImpl.CLASSE_PERSISTENTE);
        List<Tarefa> tarefas = query.getResultList();
        return tarefas;
    } // fim do método listarTudo

    @Override
    public List<Tarefa> buscarTodasPorColaborador(Colaborador colaborador) {
        TypedQuery<Tarefa> query = this.entityManager.createNamedQuery(Tarefa.NAMED_QUERY_BUSCAR_POR_COLABORADOR, TarefaDaoImpl.CLASSE_PERSISTENTE);
        query.setParameter("id", colaborador);
        List<Tarefa> tarefas = query.getResultList();
        return tarefas;
    } // fim do método buscarTodasPorIdColaborador

    @Override
    public List<Tarefa> buscarTodasPorColaboradorEStatus(Colaborador colaborador, List<Status> status) {
        TypedQuery<Tarefa> query = this.entityManager.createNamedQuery(Tarefa.NAMED_QUERY_BUSCAR_POR_COLABORADOR_E_STATUS, TarefaDaoImpl.CLASSE_PERSISTENTE);
        query.setParameter("colaborador", colaborador);
        query.setParameter("status", status);
        List<Tarefa> tarefas = query.getResultList();
        return tarefas;
    } // fim do método buscarTodasPorColaboradorEStatus

    @Override
    public List<Tarefa> buscarTodasPorStatus(List<Status> status) {
        TypedQuery<Tarefa> query = this.entityManager.createNamedQuery(Tarefa.NAMED_QUERY_BUSCAR_POR_STATUS, TarefaDaoImpl.CLASSE_PERSISTENTE);
        query.setParameter("status", status);
        List<Tarefa> tarefas = query.getResultList();
        return tarefas;
    } // fim do método buscarTodasTarefasPorStatus

    @Override
    public List<Tarefa> buscarTodasComDatasDefinidas() {
        TypedQuery<Tarefa> query = this.entityManager.createNamedQuery(Tarefa.NAMED_QUERY_BUSCAR_TODOS_DATAS_DEFINIDAS, TarefaDaoImpl.CLASSE_PERSISTENTE);
        List<Tarefa> tarefas = query.getResultList();
        return tarefas;
    } // fim do método buscarTodasPorStatusComDatasDefinidas

    @Override
    public List<Tarefa> buscarTodasPorColaboradorEStatusComDatasDefinidas(Colaborador colaborador, List<Status> status) {
        TypedQuery<Tarefa> query = this.entityManager.createNamedQuery(Tarefa.NAMED_QUERY_BUSCAR_POR_COLABORADOR_E_STATUS_DATAS_DEFINIDAS, TarefaDaoImpl.CLASSE_PERSISTENTE);
        query.setParameter("colaborador", colaborador);
        query.setParameter("status", status);
        List<Tarefa> tarefas = query.getResultList();
        return tarefas;
    } // fim do método buscarTodasPorColaboradorEStatusComDatasDefinidas

    @Override
    public List<Tarefa> buscarTodasPorStatusComDatasDefinidas(List<Status> status) {
        TypedQuery<Tarefa> query = this.entityManager.createNamedQuery(Tarefa.NAMED_QUERY_BUSCAR_POR_STATUS_DATAS_DEFINIDAS, TarefaDaoImpl.CLASSE_PERSISTENTE);
        query.setParameter("status", status);
        List<Tarefa> tarefas = query.getResultList();
        return tarefas;
    } // fim do método buscarTodasPorStatusComDatasDefinidas

    @Override
    public List<Object[]> buscarTotaisDemandasPorPeriodoDeTodasEquipes(Date inicio, Date fim) {
        Query query = this.entityManager.createNamedQuery(Tarefa.NAMED_NATIVE_QUERY_TOTAL_TODAS_EQUIPES);
        query.setParameter(1, inicio);
        query.setParameter(2, fim);
        @SuppressWarnings("unchecked")
        List<Object[]> totais = query.getResultList();
        return totais;
    } // fim do método buscarTotaisTarefasPorPeriodoDeTodasEquipes

    @Override
    public List<Object[]> buscarTotaisDemandasPorPeriodoEEquipePorTipo(Date inicio, Date fim, String tipo) {
        Query query = this.entityManager.createNamedQuery(Tarefa.NAMED_NATIVE_QUERY_TOTAL_TODAS_EQUIPES_POR_TIPO);
        query.setParameter(1, inicio);
        query.setParameter(2, fim);
        query.setParameter(3, tipo);
        @SuppressWarnings("unchecked")
        List<Object[]> totais = query.getResultList();
        return totais;
    } // fim do método buscarTotaisTarefasPorPeriodoEEquipePorTipo

    @Override
    public List<Object[]> buscarTotaisDemandasTodosSistemas(Date inicio, Date fim) {
        Query query = this.entityManager.createNamedQuery(Tarefa.NAMED_NATIVE_QUERY_TOTAL_DEMANDAS_TODOS_SISTEMAS);
        query.setParameter(1, inicio);
        query.setParameter(2, fim);
        @SuppressWarnings("unchecked")
        List<Object[]> totais = query.getResultList();
        return totais;
    } // fim do método buscarTotaisIncidentesTodosSistemas

    @Override
    public List<Object[]> buscarTotaisDemandasTodosSistemasPorEquipe(Date inicio, Date fim, Long id) {
        Query query = this.entityManager.createNamedQuery(Tarefa.NAMED_NATIVE_QUERY_TOTAL_DEMANDAS_TODOS_SISTEMAS_POR_EQUIPE);
        query.setParameter(1, inicio);
        query.setParameter(2, fim);
        query.setParameter(3, id);
        @SuppressWarnings("unchecked")
        List<Object[]> totais = query.getResultList();
        return totais;
    } // fim do método buscarTotaisIncidentesTodosSistemasPorEquipe

    @Override
    public List<Object[]> buscarTotaisDemandasTodosSistemasPorTipo(Date inicio, Date fim, String tipo) {
        Query query = this.entityManager.createNamedQuery(Tarefa.NAMED_NATIVE_QUERY_TOTAL_DEMANDAS_TODOS_SISTEMAS_POR_TIPO);
        query.setParameter(1, inicio);
        query.setParameter(2, fim);
        query.setParameter(3, tipo);
        @SuppressWarnings("unchecked")
        List<Object[]> totais = query.getResultList();
        return totais;
    } // fim do método buscarTotaisIncidentesTodosSistemasPorTipo

    @Override
    public List<Object[]> buscarTotaisDemandasTodosSistemasPorTipoEEquipe(Date inicio, Date fim, Long id, String tipo) {
        Query query = this.entityManager.createNamedQuery(Tarefa.NAMED_NATIVE_QUERY_TOTAL_DEMANDAS_TODOS_SISTEMAS_POR_EQUIPE_E_TIPO);
        query.setParameter(1, inicio);
        query.setParameter(2, fim);
        query.setParameter(3, id);
        query.setParameter(4, tipo);
        @SuppressWarnings("unchecked")
        List<Object[]> totais = query.getResultList();
        return totais;
    } // fim do método buscarTotaisIncidentesTodosSistemasPorTipoEEquipe

    @Override
    public List<Object[]> buscarTotaisDemandasTodosClientes(Date inicio, Date fim) {
        Query query = this.entityManager.createNamedQuery(Tarefa.NAMED_NATIVE_QUERY_TOTAL_DEMANDAS_TODOS_CLIENTES);
        query.setParameter(1, inicio);
        query.setParameter(2, fim);
        @SuppressWarnings("unchecked")
        List<Object[]> totais = query.getResultList();
        return totais;
    } // fim do método buscarTotaisDemandasTodosClientes

    @Override
    public List<Object[]> buscarTotaisDemandasTodosClientesPorTipo(Date inicio, Date fim, String tipo) {
        Query query = this.entityManager.createNamedQuery(Tarefa.NAMED_NATIVE_QUERY_TOTAL_DEMANDAS_TODOS_CLIENTES_POR_TIPO);
        query.setParameter(1, inicio);
        query.setParameter(2, fim);
        query.setParameter(3, tipo);
        @SuppressWarnings("unchecked")
        List<Object[]> totais = query.getResultList();
        return totais;
    } // fim do método buscarTotaisDemandasTodosClientesPorTipo

    @Override
    public List<Object[]> buscarTotaisDemandasTodosClientesPorEquipe(Date inicio, Date fim, Long id) {
        Query query = this.entityManager.createNamedQuery(Tarefa.NAMED_NATIVE_QUERY_TOTAL_DEMANDAS_TODOS_SISTEMAS_POR_EQUIPE);
        query.setParameter(1, inicio);
        query.setParameter(2, fim);
        query.setParameter(3, id);
        @SuppressWarnings("unchecked")
        List<Object[]> totais = query.getResultList();
        return totais;
    } // fim do método buscarTotaisDemandasTodosClientesPorEquipe

    @Override
    public List<Object[]> buscarTotaisDemandasTodosClientesPorEquipeETipo(Date inicio, Date fim, Long id, String tipo) {
        Query query = this.entityManager.createNamedQuery(Tarefa.NAMED_NATIVE_QUERY_QUERY_DEMANDAS_TODOS_CLIENTES_POR_EQUIPE_E_TIPO);
        query.setParameter(1, inicio);
        query.setParameter(2, fim);
        query.setParameter(3, id);
        query.setParameter(4, tipo);
        @SuppressWarnings("unchecked")
        List<Object[]> totais = query.getResultList();
        return totais;
    } // fim do método buscarTotaisDemandasTodosClientePorEquipeETipo

} // fim da classe TarefaDaoImpl
