package com.valhala.tarefa.ejb;

import com.valhala.tarefa.dao.api.TarefaDao;
import com.valhala.tarefa.exceptions.ConsultaSemRetornoException;
import com.valhala.tarefa.exceptions.CopiaDePropriedadesException;
import com.valhala.tarefa.exceptions.DaoException;
import com.valhala.tarefa.exceptions.ServiceException;
import com.valhala.tarefa.model.Colaborador;
import com.valhala.tarefa.model.Status;
import com.valhala.tarefa.model.Tarefa;
import com.valhala.tarefa.model.TipoDemanda;
import com.valhala.tarefa.qualifiers.Auditavel;
import com.valhala.tarefa.util.Copiador;

import javax.ejb.*;
import javax.inject.Inject;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * EJB responsavel pela regra de negócio relacionada a Tarefa
 *
 * @author Bruno Luiz Viana
 * @version 1.0
 * @since 23/02/2014
 */
@Auditavel
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class TarefaService {

    @Inject
    private TarefaDao tarefaDao;

    /**
     * Método utilizado para executar a ação de cadastrar uma tarefa no sistema.
     *
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
     *
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
        } catch (ConsultaSemRetornoException | CopiaDePropriedadesException | DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        } // fim do bloco try/catch
    } // fim do método editarTarefa

    /**
     * Método utilizado para executar a ação de remover uma tarefa do sistema.
     *
     * @param id
     * @throws ServiceException
     */
    @Auditavel
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void removerTarefa(Serializable id) throws ServiceException {
        try {
            this.tarefaDao.remover(this.tarefaDao.buscarPorId(id));
        } catch (ConsultaSemRetornoException | DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        } // fim do bloco try/catch
    } // fim do método removerTarefa

    /**
     * Método utilizado para executar a ação de buscar uma tarefa pelo seu id.
     *
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
     *
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
     *
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
     *
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
     *
     * @param status
     * @return
     * @throws ConsultaSemRetornoException
     */
    @Auditavel
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Tarefa> buscarTarefasPorStatus(List<Status> status) throws ConsultaSemRetornoException {
        return this.tarefaDao.buscarTodasPorStatus(status);
    } // fim do método buscarTarefasPorStatus

    @Auditavel
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Tarefa> buscarTodasTarefasComDataDefinidas() throws ConsultaSemRetornoException {
        return this.tarefaDao.buscarTodasComDatasDefinidas();
    } // fim do método buscarTodasTarefasComDataDefinidas

    @Auditavel
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Tarefa> buscarTarefasPorColaboradorEStatusComDatasDefinidas(Colaborador colaborador, List<Status> status) throws ConsultaSemRetornoException {
        return this.tarefaDao.buscarTodasPorColaboradorEStatusComDatasDefinidas(colaborador, status);
    } // fim do método buscarTodasTarefasComDataDefinidas

    @Auditavel
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Tarefa> buscarTarefasPorStatusComDatasDefinidas(List<Status> status) throws ConsultaSemRetornoException {
        return this.tarefaDao.buscarTodasPorStatusComDatasDefinidas(status);
    } // fim do método buscarTarefasPorStatusComDatasDefinidas

    @Auditavel
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Map<String, BigInteger> buscarTotaisPorEquipeEPeriodoETipo(Date inicio, Date fim, TipoDemanda tipo) throws ConsultaSemRetornoException {
        Map<String, BigInteger> mapa = new HashMap<>();
        List<Object[]> retorno;

        if (tipo.equals(TipoDemanda.TODOS)) {
            retorno = this.tarefaDao.buscarTotaisDemandasPorPeriodoDeTodasEquipes(inicio, fim);
        } else {
            retorno = this.tarefaDao.buscarTotaisDemandasPorPeriodoEEquipePorTipo(inicio, fim, tipo.name());
        } // fim do bloco if/else

        for (Object[] objeto : retorno) {
            String nome = (String) objeto[0];
            BigInteger total = (BigInteger) objeto[1];
            mapa.put(nome, total);
        } // fim do bloco for

        return mapa;
    } // fim do método buscarTotaisPorEquipeEPeriodo
    
    @Auditavel
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Map<String, BigInteger> buscarTotaisTodosSistemaPorTipoEEquipe(Date inicio, Date fim, Long id, TipoDemanda tipo) throws ConsultaSemRetornoException {
    	Map<String, BigInteger> mapa = new HashMap<>();
    	List<Object[]> retorno;
    	
    	if (id != null && !id.equals(Long.valueOf(0l))) {
			if(tipo.equals(TipoDemanda.TODOS)) {
				retorno = this.tarefaDao.buscarTotaisDemandasTodosSistemasPorEquipe(inicio, fim, id);
			} else {
				retorno = this.tarefaDao.buscarTotaisDemandasTodosSistemasPorTipoEEquipe(inicio, fim, id, tipo.name());
			} // fim do bloco if/else
		} else {
			if (tipo.equals(TipoDemanda.TODOS)) {
				retorno = this.tarefaDao.buscarTotaisDemandasTodosSistemas(inicio, fim);
			} else {
				retorno = this.tarefaDao.buscarTotaisDemandasTodosSistemasPorTipo(inicio, fim, tipo.name());
			} // fim do bloco if/else
		} // fim do bloco if/else
    	
    	for (Object[] objeto : retorno) {
			String nome = (String) objeto[0];
			BigInteger total = (BigInteger) objeto[1];
			mapa.put(nome, total);
		} // fim do bloco for
    	
    	return mapa;
    } // fim do método buscarTotaisTodosSistemaPorTipoEEquipe

} // fim da classe TarefaService