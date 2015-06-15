package com.valhala.tarefa.ejb;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;

import com.valhala.tarefa.dao.api.ClienteDao;
import com.valhala.tarefa.dao.api.ColaboradorDao;
import com.valhala.tarefa.dao.api.EquipeDao;
import com.valhala.tarefa.dao.api.SistemaDao;
import com.valhala.tarefa.dao.api.TarefaDao;
import com.valhala.tarefa.exceptions.ConsultaSemRetornoException;
import com.valhala.tarefa.exceptions.ServiceException;
import com.valhala.tarefa.model.Colaborador;
import com.valhala.tarefa.model.Status;
import com.valhala.tarefa.model.Tarefa;
import com.valhala.tarefa.model.TipoDemanda;
import com.valhala.tarefa.qualifiers.Auditavel;

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
public class TarefaBean {

    @Inject
    private TarefaDao tarefaDao;
    @Inject
    private ColaboradorDao colaboradorDao;
    @Inject
    private SistemaDao sistemaDao;
    @Inject
    private ClienteDao clienteDao;
    @Inject
    private EquipeDao equipeDao;
    
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
    	Tarefa tarefaPersistir = this.tarefaDao.buscarPorId(tarefa.getId());
    	tarefaPersistir.setId(tarefa.getId());
    	tarefaPersistir.setNumeroDemanda(tarefa.getNumeroDemanda());
    	tarefaPersistir.setTitulo(tarefa.getTitulo());
    	tarefaPersistir.setPrioridade(tarefa.getPrioridade());
    	tarefaPersistir.setTipoDemanda(tarefa.getTipoDemanda());
    	tarefaPersistir.setCategoria(tarefa.getCategoria());
    	tarefaPersistir.setAbertura(tarefa.getAbertura());
    	tarefaPersistir.setInicio(tarefa.getInicio());
    	tarefaPersistir.setDesenvolvimento(tarefa.getDesenvolvimento());
    	tarefaPersistir.setHomologacao(tarefa.getHomologacao());
    	tarefaPersistir.setFinalPlanejado(tarefa.getFinalPlanejado());
    	tarefaPersistir.setFinalEfetivo(tarefa.getFinalEfetivo());
    	tarefaPersistir.setStatus(tarefa.getStatus());
    	tarefaPersistir.setStatusSla(tarefa.getStatusSla());
    	tarefaPersistir.setObservacao(tarefa.getObservacao());
    	tarefaPersistir.setColaborador(tarefa.getColaborador() != null && tarefa.getColaborador().getId() != null  ? colaboradorDao.buscarPorId(tarefa.getColaborador().getId()) : null);
    	tarefaPersistir.setCliente(tarefa.getCliente() != null && tarefa.getCliente().getId() != null ? clienteDao.buscarPorId(tarefa.getCliente().getId()) : null);
    	tarefaPersistir.setEquipe(tarefa.getEquipe() != null && tarefa.getEquipe().getId() != null ? equipeDao.buscarPorId(tarefa.getEquipe().getId()) : null);
    	tarefaPersistir.setSistema(tarefa.getSistema() != null && tarefa.getSistema().getId() != null ? sistemaDao.buscarPorId(tarefa.getSistema().getId()) : null);
    	this.tarefaDao.atualizar(tarefaPersistir);
    } // fim do método editarTarefa

    /**
     * Método utilizado para executar a ação de remover uma tarefa do sistema.
     *
     * @param id
     * @throws ServiceException
     */
    @Auditavel
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void removerTarefa(Serializable id) {
        this.tarefaDao.remover(this.tarefaDao.buscarPorId(id));
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
    public Tarefa buscarPorId(Serializable id) {
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
    public List<Tarefa> buscarTodasTarefas() {
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
    public List<Tarefa> buscarTarefasPorColaborador(Colaborador colaborador) {
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
    public List<Tarefa> buscarTarefasPorColaboradorEStatus(Colaborador colaborador, List<Status> status) {
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
    public List<Tarefa> buscarTarefasPorStatus(List<Status> status) {
        return this.tarefaDao.buscarTodasPorStatus(status);
    } // fim do método buscarTarefasPorStatus

    @Auditavel
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Tarefa> buscarTodasTarefasComDataDefinidas() {
        return this.tarefaDao.buscarTodasComDatasDefinidas();
    } // fim do método buscarTodasTarefasComDataDefinidas

    @Auditavel
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Tarefa> buscarTarefasPorColaboradorEStatusComDatasDefinidas(Colaborador colaborador, List<Status> status) {
        return this.tarefaDao.buscarTodasPorColaboradorEStatusComDatasDefinidas(colaborador, status);
    } // fim do método buscarTodasTarefasComDataDefinidas

    @Auditavel
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Tarefa> buscarTarefasPorStatusComDatasDefinidas(List<Status> status) {
        return this.tarefaDao.buscarTodasPorStatusComDatasDefinidas(status);
    } // fim do método buscarTarefasPorStatusComDatasDefinidas

    @Auditavel
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Map<String, BigInteger> buscarTotaisPorEquipeEPeriodoETipo(Date inicio, Date fim, TipoDemanda tipo) {
        Map<String, BigInteger> mapa = new HashMap<>();
        List<Object[]> retorno;

        retorno = tipo.equals(TipoDemanda.TODOS)? 
        		this.tarefaDao.buscarTotaisDemandasPorPeriodoDeTodasEquipes(inicio, fim) : 
        		this.tarefaDao.buscarTotaisDemandasPorPeriodoEEquipePorTipo(inicio, fim, tipo.name());
        
        for (Object[] objeto : retorno) {
            String nome = (String) objeto[0];
            BigInteger total = (BigInteger) objeto[1];
            mapa.put(nome, total);
        } // fim do bloco for

        return mapa;
    } // fim do método buscarTotaisPorEquipeEPeriodo
    
    @Auditavel
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Map<String, BigInteger> buscarTotaisTodosSistemaPorTipoEEquipe(Date inicio, Date fim, Long id, TipoDemanda tipo) {
    	Map<String, BigInteger> mapa = new HashMap<>();
    	List<Object[]> retorno;
    	
    	if (id != null && !id.equals(Long.valueOf(0l))) {
			retorno = tipo.equals(TipoDemanda.TODOS) ? 
					this.tarefaDao.buscarTotaisDemandasTodosSistemasPorEquipe(inicio, fim, id) : 
					this.tarefaDao.buscarTotaisDemandasTodosSistemasPorTipoEEquipe(inicio, fim, id, tipo.name());
		} else {
			retorno = tipo.equals(TipoDemanda.TODOS) ?
					this.tarefaDao.buscarTotaisDemandasTodosSistemas(inicio, fim) :
					this.tarefaDao.buscarTotaisDemandasTodosSistemasPorTipo(inicio, fim, tipo.name());	
		} // fim do bloco if/else
    	
    	for (Object[] objeto : retorno) {
			String nome = (String) objeto[0];
			BigInteger total = (BigInteger) objeto[1];
			mapa.put(nome, total);
		} // fim do bloco for
    	
    	return mapa;
    } // fim do método buscarTotaisTodosSistemaPorTipoEEquipe
    
    @Auditavel
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Map<String, BigInteger> buscarTotaisTodosClientesPorTipoEEquipe(Date inicio, Date fim, Long id, TipoDemanda tipo) {
    	Map<String, BigInteger> mapa = new HashMap<>();
    	List<Object[]> retorno;
    	
    	if (id != null && !id.equals(Long.valueOf(0l))) {
			retorno = tipo.equals(TipoDemanda.TODOS) ? 
					this.tarefaDao.buscarTotaisDemandasTodosClientesPorEquipe(inicio, fim, id) : 
					this.tarefaDao.buscarTotaisDemandasTodosClientesPorEquipeETipo(inicio, fim, id, tipo.name());
    	} else {
    		retorno = tipo.equals(TipoDemanda.TODOS) ? 
    				this.tarefaDao.buscarTotaisDemandasTodosClientes(inicio, fim) : 
    				this.tarefaDao.buscarTotaisDemandasTodosClientesPorTipo(inicio, fim, tipo.name());
		} // fim do bloco if/else
    	
    	for (Object[] objeto : retorno) {
			String nome = (String) objeto[0];
			BigInteger total = (BigInteger) objeto[1];
			mapa.put(nome, total);
		} // fim do bloco for
    	
    	return mapa;
    }// fim do método buscarTotaisTodosClientesPorTipoEEquipe

} // fim da classe TarefaService
