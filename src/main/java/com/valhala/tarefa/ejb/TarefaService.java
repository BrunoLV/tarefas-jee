package com.valhala.tarefa.ejb;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;

import com.valhala.tarefa.dao.api.TarefaDao;
import com.valhala.tarefa.exceptions.ConsultaSemRetornoException;
import com.valhala.tarefa.exceptions.CopiaDePropriedadesException;
import com.valhala.tarefa.exceptions.ServiceException;
import com.valhala.tarefa.model.Colaborador;
import com.valhala.tarefa.model.Status;
import com.valhala.tarefa.model.Tarefa;
import com.valhala.tarefa.qualifiers.Auditavel;
import com.valhala.tarefa.util.Copiador;

/**
 * EJB responsavel pela regra de negócio relacionada a Tarefa
 * @author Bruno Luiz Viana
 * @version 1.0
 * @since 23/02/2014
 *
 */
@Auditavel
@Stateless @TransactionManagement(TransactionManagementType.CONTAINER)
public class TarefaService {
	
	@Inject
	private TarefaDao tarefaDao;
	
	/**
	 * Método utilizado para executar a ação de cadastrar uma tarefa no sistema.
	 * @param tarefa
	 * @throws ServiceException
	 */
	@Auditavel
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void cadastrarTarefa(Tarefa tarefa) throws ServiceException {
		try {
			this.tarefaDao.persistir(tarefa);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		} // fim do bloco try/catch
	} // fim do método cadastrarTarefa
	
	/**
	 * Método utilizado para executar a ação de editar uma tarefa no sistema.
	 * @param tarefa
	 * @throws ServiceException
	 */
	@Auditavel
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void editarTarefa(Tarefa tarefa) throws ServiceException {
		Tarefa tarefaPersitente;
		try {
			tarefaPersitente = this.tarefaDao.buscarPorId(tarefa.getId());
			Copiador.copiar(Tarefa.class, tarefaPersitente, tarefa);
			this.tarefaDao.atualizar(tarefaPersitente);
		} catch (ConsultaSemRetornoException | CopiaDePropriedadesException e) {
			throw new ServiceException(e.getMessage(), e);
		} // fim do bloco try/catch
	} // fim do método editarTarefa
	
	/**
	 * Método utilizado para executar a ação de remover uma tarefa do sistema.
	 * @param id
	 * @throws ServiceException
	 */
	@Auditavel
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void removerTarefa(Serializable id) throws ServiceException {
		try {
			this.tarefaDao.remover(this.tarefaDao.buscarPorId(id));
		} catch (ConsultaSemRetornoException e) {
			throw new ServiceException(e.getMessage(), e);
		} // fim do bloco try/catch
	} // fim do método removerTarefa
	
	/**
	 * Método utilizado para executar a ação de buscar uma tarefa pelo seu id.
	 * @param id
	 * @return
	 * @throws ConsultaSemRetornoException
	 */
	@Auditavel
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Tarefa buscarPorId(Serializable id) throws ConsultaSemRetornoException {
		return this.tarefaDao.buscarPorId(id);
	} // fim do método buscarPorId
	
	/**
	 * Método utilizado para executar a ação de buscar todas as tarefas cadastradas.
	 * @return
	 * @throws ConsultaSemRetornoException
	 */
	@Auditavel
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Tarefa> buscarTodasTarefas() throws ConsultaSemRetornoException {
		return this.tarefaDao.listarTudo();
	} // fim do método buscarTodasTarefas
	
	/**
	 * Método utilizado para executar a ação de buscar todas as tarefas de um colaborador.
	 * @param colaborador
	 * @return
	 * @throws ConsultaSemRetornoException
	 */
	@Auditavel
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Tarefa> buscarTarefasPorColaborador(Colaborador colaborador) throws ConsultaSemRetornoException {
		return this.tarefaDao.buscarTodasPorColaborador(colaborador);
	} // fim do método buscarTarefasPorColaborador
	
	/**
	 * Método utilizado para executar a ação de buscar todas as tarefas de um colaborador e por status.
	 * @param colaborador
	 * @param status
	 * @return
	 * @throws ConsultaSemRetornoException
	 */
	@Auditavel
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Tarefa> buscarTarefasPorColaboradorEStatus(Colaborador colaborador, List<Status> status) throws ConsultaSemRetornoException {
		return this.tarefaDao.buscarTodasPorColaboradorEStatus(colaborador, status);
	} // fim do método buscarTarefasPorColaboradorEStatus 
	
	/**
	 * Método utilizado para executar a ação de terafas por status.
	 * @param status
	 * @return
	 * @throws ConsultaSemRetornoException
	 */
	@Auditavel
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Tarefa> buscarTarefasPorStatus(List<Status> status) throws ConsultaSemRetornoException {
		return this.tarefaDao.buscarTodasPorStatus(status);
	} // fim do método buscarTarefasPorStatus

} // fim da classe TarefaService