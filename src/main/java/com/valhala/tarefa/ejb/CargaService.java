package com.valhala.tarefa.ejb;

import java.util.List;

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
import com.valhala.tarefa.dao.api.EquipeDao;
import com.valhala.tarefa.dao.api.SistemaDao;
import com.valhala.tarefa.dao.api.TarefaDao;
import com.valhala.tarefa.exceptions.ConsultaSemRetornoException;
import com.valhala.tarefa.exceptions.DaoException;
import com.valhala.tarefa.model.Cliente;
import com.valhala.tarefa.model.Colaborador;
import com.valhala.tarefa.model.Equipe;
import com.valhala.tarefa.model.Sistema;
import com.valhala.tarefa.model.Tarefa;
import com.valhala.tarefa.qualifiers.Auditavel;

/**
 * Enterprise bean utilizado para as cargas assincronas de Tarefas, Sistemas e demais entidades no banco de dados.
 *
 * @author Bruno Luiz Viana
 * @version 1.0
 * @since 04/06/2014
 */
@Stateless
@Auditavel
@TransactionManagement(TransactionManagementType.BEAN)
public class CargaService {

	@Inject
	private SistemaDao sistemaDao;
	@Inject
	private ClienteDao clienteDao;
	@Inject
	private EquipeDao equipeDao;
	@Inject
	private TarefaDao tarefaDao;
	@Inject
	private ColaboradorDao colaboradorDao;

	@Resource
	private UserTransaction transaction;

	/**
	 * Método assincrono utilizado para executar a carga de sistemas no banco de dados.
	 *
	 * @param stream
	 */
	@Auditavel
	public void executarCargaSistema(List<Sistema> sistemas) {
		for (Sistema sistema : sistemas) {
			try {
				transaction.begin();
				this.sistemaDao.persistir(sistema);
				transaction.commit();
			} catch (DaoException | SecurityException | IllegalStateException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SystemException | NotSupportedException e) {
				try {
					transaction.rollback();
					continue;
				} catch (IllegalStateException | SecurityException | SystemException e1) {
					continue;
				} // fim do bloco try/catch
			} // fim do bloco try/catch
		} // fim do bloco for
	} // fim do método executarCargaSistema

	/**
	 * Método assincrono utilizado para executar a carga de clientes no banco de dados.
	 *
	 * @param stream
	 */
	@Auditavel
	public void executarCargaCliente(List<Cliente> clientes) {
		for (Cliente cliente : clientes) {
			try {
				this.transaction.begin();
				this.clienteDao.persistir(cliente);
				this.transaction.commit();
			} catch (DaoException | NotSupportedException | SystemException | SecurityException | IllegalStateException | RollbackException | HeuristicMixedException | HeuristicRollbackException e) {
				try {
					this.transaction.rollback();
				} catch (IllegalStateException | SecurityException | SystemException e1) {
					continue;
				} // fim do bloco try/catch
			} // fim do bloco try/catch
		} // fim do bloco for
		//enviarEmailProcessamentoArquivo(TipoCarga.CLIENTES, map);
	} // fim do bloco executarCargaCliente

	/**
	 * Método assincrono utilizado para executar a carga de equipes no banco de dados.
	 *
	 * @param stream
	 */
	@Auditavel
	public void executarCargaEquipe(List<Equipe> equipes) {
		for (Equipe equipe : equipes) {
			try {
				this.transaction.begin();
				equipeDao.persistir(equipe);
				this.transaction.commit();
			} catch (DaoException | NotSupportedException | SystemException | SecurityException | IllegalStateException | RollbackException | HeuristicMixedException | HeuristicRollbackException e) {
				try {
					this.transaction.rollback();
				} catch (IllegalStateException | SecurityException | SystemException e1) {
					continue;
				} // fim do bloco try/catch
			} // fim do bloco try/catch
		} // fim do bloco for
		//enviarEmailProcessamentoArquivo(TipoCarga.EQUIPES, map);

	} // fim do método executarCargaEquipe

	/**
	 * Método assincrono utilizado para executar a carga de colaboradores no banco de dados.
	 *
	 * @param stream
	 */
	@Auditavel
	public void executarCargaColaborador(List<Colaborador> colaboradores) {
		for (Colaborador colaborador : colaboradores) {
			colaborador.setSenha(colaborador.getMatricula());
			try {
				this.transaction.begin();
				this.colaboradorDao.persistir(colaborador);
				this.transaction.commit();
			} catch (DaoException | NotSupportedException | SystemException | SecurityException | IllegalStateException | RollbackException | HeuristicMixedException | HeuristicRollbackException e) {
				try {
					this.transaction.rollback();
				} catch (IllegalStateException | SecurityException | SystemException e1) {
					continue;
				} // fim do bloco try/catch
			} // fim do bloco try/catch
		} // fim do bloco for
	} // fim do método executarCargaColaborador

	/**
	 * Método assincrono utilizado para executar a carga de tarefas no banco de dados.
	 *
	 * @param stream
	 */
	@Auditavel
	public void executarCargaTarefas(List<Tarefa> tarefas) {
		for (Tarefa tarefa : tarefas) {
			try {
				this.transaction.begin();
				tarefa.setColaborador(tarefa.getColaborador() != null ? this.colaboradorDao.buscarPorNome(tarefa.getColaborador().getNome()) : null);
				tarefa.setEquipe(tarefa.getEquipe() != null ? this.equipeDao.buscarPorNome(tarefa.getEquipe().getNome()) : null);
				this.tarefaDao.persistir(tarefa);
				this.transaction.commit();
			} catch (ConsultaSemRetornoException | DaoException | NotSupportedException | SystemException | SecurityException | IllegalStateException | RollbackException | HeuristicMixedException | HeuristicRollbackException e) {
				try {
					System.out.println("Tarefa " + tarefa.getNumeroDemanda() + " não inserida.");
					this.transaction.rollback();
					continue;
				} catch (IllegalStateException | SecurityException | SystemException e1) {
					continue;
				} // fim do bloco try/catch
			} // fim do bloco try/catch
		} // fim do bloco for
	} // fim do método executarCargaTarefas

} // fim do método CargaService