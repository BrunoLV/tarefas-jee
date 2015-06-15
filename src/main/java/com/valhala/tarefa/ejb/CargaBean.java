package com.valhala.tarefa.ejb;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import com.valhala.tarefa.dao.api.ClienteDao;
import com.valhala.tarefa.dao.api.ColaboradorDao;
import com.valhala.tarefa.dao.api.Dao;
import com.valhala.tarefa.dao.api.EquipeDao;
import com.valhala.tarefa.dao.api.SistemaDao;
import com.valhala.tarefa.dao.api.TarefaDao;
import com.valhala.tarefa.file.carga.TipoCarga;
import com.valhala.tarefa.model.Colaborador;
import com.valhala.tarefa.model.Tarefa;
import com.valhala.tarefa.qualifiers.Auditavel;

/**
 * Enterprise bean utilizado para as cargas assincronas de Tarefas, Sistemas e demais entidades no banco de dados.
 *
 * @author Bruno Luiz Viana
 * @version 1.0
 * @since 04/06/2014
 */
@Stateless @Auditavel
@TransactionManagement(TransactionManagementType.BEAN)
public class CargaBean {

	@Inject private SistemaDao sistemaDao;
	@Inject private ClienteDao clienteDao;
	@Inject private EquipeDao equipeDao;
	@Inject private TarefaDao tarefaDao;
	@Inject private ColaboradorDao colaboradorDao;

	@Resource
	private UserTransaction transaction;

	@Auditavel
	public void executarCarga(Set<? extends Object> set, TipoCarga carga) {

		switch (carga) {
		case TAREFAS:
			Set<Tarefa> tarefas = new HashSet<>();
			for (Object object : set) {
				Tarefa tarefa = montarInsercaoTarefa((Tarefa) object);
				tarefas.add(tarefa);
			}
			executar(tarefas, tarefaDao);
			break;
		case CLIENTES:
			executar(set, clienteDao);
			break;
		case COLABORADORES:
			Set<Colaborador> colaboradores = new HashSet<>();
			for (Object object : set) {
				Colaborador colaborador = montarInsercaoColaborador((Colaborador) object);
				colaboradores.add(colaborador);
			}
			executar(colaboradores, colaboradorDao);
			break;
		case EQUIPES:
			executar(set, equipeDao);
			break;
		case SISTEMAS:
			executar(set, sistemaDao);
			break;
		default:
			break;
		}
	}

	@SuppressWarnings("unchecked")
	private <T> void executar(Set<? extends Object> set, Dao<T> dao) {
		for (Iterator<T> iterator = (Iterator<T>) set.iterator(); iterator.hasNext();) {
			try {
				transaction.begin();
				T object = (T) iterator.next();
				ExecutorCarga.executarCarga(object, dao);
				transaction.commit();
			} catch (SecurityException | IllegalStateException | NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException e) {
				try {
					transaction.rollback();
				} catch (IllegalStateException | SecurityException | SystemException e1) {
					e1.printStackTrace();
				}
			}
		} // fim do bloco for
	}

	private Colaborador montarInsercaoColaborador(Colaborador colaborador) {
		Colaborador colaboradorAInserir = new Colaborador.Builder(colaborador.getNome(), colaborador.getMatricula()).
				senha(colaborador.getMatricula()).build();
		return colaboradorAInserir;
	}

	private Tarefa montarInsercaoTarefa(Tarefa tarefa) {
		Tarefa tarefaAInserir = new Tarefa.Builder(tarefa.getNumeroDemanda(), tarefa.getTitulo(), tarefa.getPrioridade(), tarefa.getTipoDemanda()).
				abertura(tarefa.getAbertura()).
				inicio(tarefa.getInicio()).
				desenvolvimento(tarefa.getDesenvolvimento()).
				homologacao(tarefa.getHomologacao()).
				finalPlanejado(tarefa.getFinalPlanejado()).
				finalEfetivo(tarefa.getFinalEfetivo()).
				observacao(tarefa.getObservacao()).
				status(tarefa.getStatus()).
				statusSla(tarefa.getStatusSla()).
				categoria(tarefa.getCategoria()).
				cliente(null).
				sistema(null).
				colaborador(tarefa.getColaborador() != null ? this.colaboradorDao.buscarPorNome(tarefa.getColaborador().getNome()) : null).
				equipe(tarefa.getEquipe() != null ? this.equipeDao.buscarPorNome(tarefa.getEquipe().getNome()) : null).build();
		return tarefaAInserir;
	}
	
	private static final class ExecutorCarga<T> {
		
		public static <T>void executarCarga(T item, Dao<T> dao) {
			dao.persistir(item);
		}

	}

} // fim do método CargaService