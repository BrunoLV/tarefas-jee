package com.valhala.tarefa.dao.jpa;

import java.math.BigInteger;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
class TarefaDaoJPAImpl extends BaseJPADao<Tarefa> implements TarefaDao {

    public TarefaDaoJPAImpl() {
        this.classePersistente = Tarefa.class;
    } // fim do método construtor

    @Override
    public List<Tarefa> listarTudo() {
        TypedQuery<Tarefa> query = construirTypedQuery(
        		Tarefa.NAMED_QUERY_BUSCAR_TODOS);
        List<Tarefa> tarefas = query.getResultList();
        return tarefas;
    } // fim do método listarTudo

    @Override
    public List<Tarefa> buscarTodasPorColaborador(Colaborador colaborador) {
        TypedQuery<Tarefa> query = construirTypedQuery(
        		Tarefa.NAMED_QUERY_BUSCAR_POR_COLABORADOR, 
        		colaborador);
        List<Tarefa> tarefas = query.getResultList();
        return tarefas;
    } // fim do método buscarTodasPorIdColaborador

    @Override
    public List<Tarefa> buscarTodasPorColaboradorEStatus(Colaborador colaborador, List<Status> status) {
        TypedQuery<Tarefa> query = construirTypedQuery(
        		Tarefa.NAMED_QUERY_BUSCAR_POR_COLABORADOR_E_STATUS,
        		colaborador, 
        		status);
        List<Tarefa> tarefas = query.getResultList();
        return tarefas;
    } // fim do método buscarTodasPorColaboradorEStatus

    @Override
    public List<Tarefa> buscarTodasPorStatus(List<Status> status) {
        TypedQuery<Tarefa> query = construirTypedQuery(
        		Tarefa.NAMED_QUERY_BUSCAR_POR_STATUS, 
        		status);
        List<Tarefa> tarefas = query.getResultList();
        return tarefas;
    } // fim do método buscarTodasTarefasPorStatus

    @Override
    public List<Tarefa> buscarTodasComDatasDefinidas() {
        TypedQuery<Tarefa> query = construirTypedQuery(
        		Tarefa.NAMED_QUERY_BUSCAR_TODOS_DATAS_DEFINIDAS);
        List<Tarefa> tarefas = query.getResultList();
        return tarefas;
    } // fim do método buscarTodasPorStatusComDatasDefinidas

    @Override
    public List<Tarefa> buscarTodasPorColaboradorEStatusComDatasDefinidas(Colaborador colaborador, List<Status> status) {
        TypedQuery<Tarefa> query = construirTypedQuery(
        		Tarefa.NAMED_QUERY_BUSCAR_POR_COLABORADOR_E_STATUS_DATAS_DEFINIDAS, 
        		colaborador, 
        		status);
        List<Tarefa> tarefas = query.getResultList();
        return tarefas;
    } // fim do método buscarTodasPorColaboradorEStatusComDatasDefinidas

    @Override
    public List<Tarefa> buscarTodasPorStatusComDatasDefinidas(List<Status> status) {
        TypedQuery<Tarefa> query = construirTypedQuery(
        		Tarefa.NAMED_QUERY_BUSCAR_POR_STATUS_DATAS_DEFINIDAS, 
        		status); 
        List<Tarefa> tarefas = query.getResultList();
        return tarefas;
    } // fim do método buscarTodasPorStatusComDatasDefinidas

    @Override
    public Map<String, BigInteger> buscarTotaisDemandasPorPeriodoDeTodasEquipes(Date inicio, Date fim) {
        Query query = construirNamedQuery(
        		Tarefa.NAMED_NATIVE_QUERY_TOTAL_TODAS_EQUIPES, 
        		inicio, 
        		fim);
        @SuppressWarnings("unchecked")
        List<Object[]> totais = query.getResultList();
        return retornarMapaDeTotais(totais);
    } // fim do método buscarTotaisTarefasPorPeriodoDeTodasEquipes

    @Override
    public Map<String, BigInteger> buscarTotaisDemandasPorPeriodoEEquipePorTipo(Date inicio, Date fim, String tipo) {
        Query query = construirNamedQuery(
        		Tarefa.NAMED_NATIVE_QUERY_TOTAL_TODAS_EQUIPES_POR_TIPO, 
        		inicio, 
        		fim, 
        		tipo);
        @SuppressWarnings("unchecked")
        List<Object[]> totais = query.getResultList();
        return retornarMapaDeTotais(totais);
    } // fim do método buscarTotaisTarefasPorPeriodoEEquipePorTipo

    @Override
    public Map<String, BigInteger> buscarTotaisDemandasTodosSistemas(Date inicio, Date fim) {
        Query query = construirNamedQuery(
        		Tarefa.NAMED_NATIVE_QUERY_TOTAL_DEMANDAS_TODOS_SISTEMAS, 
        		inicio, 
        		fim); 
        @SuppressWarnings("unchecked")
        List<Object[]> totais = query.getResultList();
        return retornarMapaDeTotais(totais);
    } // fim do método buscarTotaisIncidentesTodosSistemas

    @Override
    public Map<String, BigInteger> buscarTotaisDemandasTodosSistemasPorEquipe(Date inicio, Date fim, Long idEquipe) {
        Query query = construirNamedQuery(
        		Tarefa.NAMED_NATIVE_QUERY_TOTAL_DEMANDAS_TODOS_SISTEMAS_POR_EQUIPE, 
        		inicio, 
        		fim, 
        		idEquipe);
        @SuppressWarnings("unchecked")
        List<Object[]> totais = query.getResultList();
        return retornarMapaDeTotais(totais);
    } // fim do método buscarTotaisIncidentesTodosSistemasPorEquipe

    @Override
    public Map<String, BigInteger> buscarTotaisDemandasTodosSistemasPorTipo(Date inicio, Date fim, String tipo) {
        Query query = construirNamedQuery(
        		Tarefa.NAMED_NATIVE_QUERY_TOTAL_DEMANDAS_TODOS_SISTEMAS_POR_TIPO, 
        		inicio, 
        		fim, 
        		tipo);
        @SuppressWarnings("unchecked")
        List<Object[]> totais = query.getResultList();
        return retornarMapaDeTotais(totais);
    } // fim do método buscarTotaisIncidentesTodosSistemasPorTipo

    @Override
    public Map<String, BigInteger> buscarTotaisDemandasTodosSistemasPorTipoEEquipe(Date inicio, Date fim, Long idEquipe, String tipo) {
        Query query = construirNamedQuery(
        		Tarefa.NAMED_NATIVE_QUERY_TOTAL_DEMANDAS_TODOS_SISTEMAS_POR_EQUIPE_E_TIPO, 
        		inicio, 
        		fim, 
        		idEquipe, 
        		tipo);
        @SuppressWarnings("unchecked")
        List<Object[]> totais = query.getResultList();
        return retornarMapaDeTotais(totais);
    } // fim do método buscarTotaisIncidentesTodosSistemasPorTipoEEquipe

    @Override
    public Map<String, BigInteger> buscarTotaisDemandasTodosClientes(Date inicio, Date fim) {
        Query query = construirNamedQuery(
        		Tarefa.NAMED_NATIVE_QUERY_TOTAL_DEMANDAS_TODOS_CLIENTES, 
        		inicio, 
        		fim); 
        @SuppressWarnings("unchecked")
        List<Object[]> totais = query.getResultList();
        return retornarMapaDeTotais(totais);
    } // fim do método buscarTotaisDemandasTodosClientes

    @Override
    public Map<String, BigInteger> buscarTotaisDemandasTodosClientesPorTipo(Date inicio, Date fim, String tipo) {
        Query query = construirNamedQuery(
        		Tarefa.NAMED_NATIVE_QUERY_TOTAL_DEMANDAS_TODOS_CLIENTES_POR_TIPO, 
        		inicio, 
        		fim, 
        		tipo); 
        @SuppressWarnings("unchecked")
        List<Object[]> totais = query.getResultList();
        return retornarMapaDeTotais(totais);
    } // fim do método buscarTotaisDemandasTodosClientesPorTipo

    @Override
    public Map<String, BigInteger> buscarTotaisDemandasTodosClientesPorEquipe(Date inicio, Date fim, Long idEquipe) {
        Query query = construirNamedQuery(
        		Tarefa.NAMED_NATIVE_QUERY_TOTAL_DEMANDAS_TODOS_SISTEMAS_POR_EQUIPE, 
        		inicio, 
        		fim, 
        		idEquipe); 
        @SuppressWarnings("unchecked")
        List<Object[]> totais = query.getResultList();
        return retornarMapaDeTotais(totais);
    } // fim do método buscarTotaisDemandasTodosClientesPorEquipe

    @Override
    public Map<String, BigInteger> buscarTotaisDemandasTodosClientesPorEquipeETipo(Date inicio, Date fim, Long idEquipe, String tipo) {
        Query query = construirNamedQuery(
        		Tarefa.NAMED_NATIVE_QUERY_QUERY_DEMANDAS_TODOS_CLIENTES_POR_EQUIPE_E_TIPO, 
        		inicio, 
        		fim, 
        		idEquipe, 
        		tipo); 
        @SuppressWarnings("unchecked")
        List<Object[]> totais = query.getResultList();
        return retornarMapaDeTotais(totais);
    } // fim do método buscarTotaisDemandasTodosClientePorEquipeETipo
    
    /*
     * Metodo utilizado para retornar um mapa dos valores retornados do banco de dados.
     */
    private Map<String, BigInteger> retornarMapaDeTotais(List<Object[]> totais) {
		Map<String, BigInteger> mapaTotais;
		if (totais.isEmpty()) {
			mapaTotais = Collections.emptyMap();
		} else {
			mapaTotais = new HashMap<>();
			for (Object[] objetoDeTotal : totais) {
				mapaTotais.put((String)objetoDeTotal[0], (BigInteger)objetoDeTotal[1]);
			} // fim do bloco for
		} // fim do bloco try/catch
		return Collections.unmodifiableMap(mapaTotais);
	} // fim do metodo retornarMapaDeTotais

} // fim da classe TarefaDaoImpl
