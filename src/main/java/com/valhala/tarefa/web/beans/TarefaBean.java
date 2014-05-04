package com.valhala.tarefa.web.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleModel;

import com.valhala.tarefa.ejb.ClienteService;
import com.valhala.tarefa.ejb.ColaboradorService;
import com.valhala.tarefa.ejb.EquipeService;
import com.valhala.tarefa.ejb.SistemaService;
import com.valhala.tarefa.ejb.TarefaService;
import com.valhala.tarefa.exceptions.ConsultaSemRetornoException;
import com.valhala.tarefa.exceptions.CopiaDePropriedadesException;
import com.valhala.tarefa.exceptions.ServiceException;
import com.valhala.tarefa.model.Cliente;
import com.valhala.tarefa.model.Colaborador;
import com.valhala.tarefa.model.Equipe;
import com.valhala.tarefa.model.Prioridade;
import com.valhala.tarefa.model.Sistema;
import com.valhala.tarefa.model.Status;
import com.valhala.tarefa.model.Tarefa;
import com.valhala.tarefa.util.Copiador;

/**
 * Managed Bean utilizado para as ações de tela relacionadas a tarefas.
 * @author Bruno Luiz Viana
 * @version 1.0
 * @since 23/02/2014
 *
 */
@Named("TarefaBean")
@RequestScoped
public class TarefaBean extends BaseJSFBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final String CHAVE_OBJETO_EDICAO = "TAREFA_EDICAO";
	private static final String CHAVE_ID_CONSULTA_SCHEDULE = "ID_CONSULTA_SCHEDULE";

	private static final String OUTCOME_ENVIAR_EDICAO = "/pages/manter/cadastro-tarefas.xhtml?faces-redirect=true";
	private static final String OUTCOME_ENVIAR_AJUSTE = "/pages/lider/ajuste-tarefas-administrativo.xhtml?faces-redirect=true";

	@EJB
	private TarefaService tarefaService;
	@EJB
	private ClienteService clienteService;
	@EJB
	private SistemaService sistemaService;
	@EJB
	private EquipeService equipeService;
	@EJB
	private ColaboradorService colaboradorService;

	
	private ScheduleModel scheduleModel;
	
	private Tarefa tarefa;
	
	private Long idConsultaSchedule;

	public TarefaBean() {
	}

	public Tarefa getTarefa() {
		return tarefa;
	}
	
	public Long getIdConsultaSchedule() {
		return idConsultaSchedule;
	}
	
	public void setIdConsultaSchedule(Long idConsultaSchedule) {
		this.idConsultaSchedule = idConsultaSchedule;
	}

	public void setTarefa(Tarefa tarefa) {
		this.tarefa = tarefa;
	}
	
	public ScheduleModel getScheduleModel() {
		return scheduleModel;
	}
	
	public void setScheduleModel(ScheduleModel scheduleModel) {
		this.scheduleModel = scheduleModel;
	}

	@PostConstruct
	public void inicializarBean() {
		this.setScheduleModel(montarScheduleTarefas(obterObjectDaSession(TarefaBean.CHAVE_ID_CONSULTA_SCHEDULE) != null ? (Long)obterObjectDaSession(TarefaBean.CHAVE_ID_CONSULTA_SCHEDULE) : 0l));
		if (obterObjectDaSession(TarefaBean.CHAVE_OBJETO_EDICAO) != null) {
			try {
				this.tarefa = new Tarefa();
				Copiador.copiar(Tarefa.class, this.tarefa, obterObjectDaSession(TarefaBean.CHAVE_OBJETO_EDICAO));
				removerObjetoDaSession(TarefaBean.CHAVE_OBJETO_EDICAO);
			} catch (CopiaDePropriedadesException e) {
				inserirMensagemDeErro(e.getMessage());
			} // fim do bloco try/catch
		} else {
			this.tarefa = new Tarefa();
		} // fim do bloco if/else
	} // fim do método

	public List<Tarefa> listarPorColaborador(Colaborador colaborador) {
		List<Tarefa> tarefas;
		try {
			List<Status> status = new ArrayList<>(Arrays.asList(Status.values()));
			status.remove(Status.CONCLUIDO);
			tarefas = this.tarefaService.buscarTarefasPorColaboradorEStatus(colaborador, status);
		} catch (ConsultaSemRetornoException e) {
			tarefas = new ArrayList<>();
		}
		return tarefas;
	}
	
	public List<Tarefa> buscarTodosConcluidos() {
		List<Tarefa> tarefas;
		List<Status> status = new ArrayList<>();
		status.add(Status.CONCLUIDO);
		try {
			tarefas = this.tarefaService.buscarTarefasPorStatus(status);
		} catch (ConsultaSemRetornoException e) {
			tarefas = new ArrayList<>();
		}
		return tarefas;
	}

	public Prioridade[] getPrioridades() {
		return Prioridade.values();
	}

	public Status[] getStatus() {
		return Status.values();
	}

	public void salvarTarefa() {
		tarefa.setFinalEfetivo(tarefa.getStatus().equals(Status.CONCLUIDO) ? new Date() : null);
		try {
			if (tarefa.getId() != null && tarefa.getId() > 0) {
				this.tarefaService.editarTarefa(tarefa);
				inserirMensagemDeSucesso("Registro atualizado com sucesso.");
			} else {
				tarefa.setId(null);
				tarefa.setAbertura(new Date());
				this.tarefaService.cadastrarTarefa(tarefa);
				tarefa = new Tarefa();
				inserirMensagemDeSucesso("Registro inserido com sucesso.");
			}
		} catch (ServiceException e) {
			inserirMensagemDeErro(e.getMessage());
		}
	}

	public void removerTarefa(Serializable id) {
		try {
			this.tarefaService.removerTarefa(id);
			inserirMensagemDeSucesso("Registro removido com sucesso.");
		} catch (ServiceException e) {
			inserirMensagemDeErro(e.getMessage());
		} 
	}

	public String enviarTarefaParaEdicao(Serializable id) {
		String outcome = null;
		try {
			Tarefa tarefa = this.tarefaService.buscarPorId(id);
			//inserirObjetoNoFlashScope(TarefaBean.CHAVE_OBJETO_EDICAO, tarefa);
			inserirObjetoNaSession(TarefaBean.CHAVE_OBJETO_EDICAO, tarefa);
			outcome = TarefaBean.OUTCOME_ENVIAR_EDICAO;
		} catch (ConsultaSemRetornoException e) {
			inserirMensagemDeErro(e.getMessage());
		} 
		return outcome;
	}
	
	public String enviarTarefaParaAjusteAdministrativo(Serializable id) {
		String outcome = null;
		try {
			Tarefa tarefa = this.tarefaService.buscarPorId(id);
			//inserirObjetoNoFlashScope(TarefaBean.CHAVE_OBJETO_EDICAO, tarefa);
			inserirObjetoNaSession(TarefaBean.CHAVE_OBJETO_EDICAO, tarefa);
			outcome = TarefaBean.OUTCOME_ENVIAR_AJUSTE;
		} catch (ConsultaSemRetornoException e) {
			inserirMensagemDeErro(e.getMessage());
		} 
		return outcome;
	}
	
	public List<Sistema> getSistemas() {
		List<Sistema> sistemas;
		try {
			sistemas = this.sistemaService.buscarTodosSistemas();
		} catch (ConsultaSemRetornoException e) {
			sistemas = new ArrayList<Sistema>();
		}
		return sistemas;
	}
	
	public List<Cliente> getClientes() {
		List<Cliente> clientes;
		try {
			clientes = this.clienteService.buscarTodosClientes();
		} catch (ConsultaSemRetornoException e) {
			clientes = new ArrayList<Cliente>();
		}
		return clientes;
	}
	
	public List<Equipe> getEquipes() {
		List<Equipe> equipes;
		try {
			equipes = this.equipeService.buscarTodasEquipes();
		} catch (ConsultaSemRetornoException e) {
			equipes = new ArrayList<Equipe>();
		}
		return equipes;
	}
	
	public List<Colaborador> getColaboradores() {
		List<Colaborador> colaboradores;
		try {
			colaboradores = this.colaboradorService.buscarTodosColaboradores();
		} catch (ConsultaSemRetornoException e) {
			colaboradores = new ArrayList<Colaborador>();
		}
		return colaboradores;
	}
	
	public List<Tarefa> getTarefasNaoConcluidas() {
		List<Tarefa> tarefas;
		List<Status> status = new ArrayList<>(Arrays.asList(Status.values()));
		status.remove(Status.CONCLUIDO);
		try {
			tarefas = this.tarefaService.buscarTarefasPorStatus(status);
		} catch (ConsultaSemRetornoException e) {
			tarefas = new ArrayList<Tarefa>();
		}
		return tarefas;
	}
	
	public void filtrarSchedule() {
		inserirObjetoNaSession(TarefaBean.CHAVE_ID_CONSULTA_SCHEDULE, this.idConsultaSchedule);
	}
	
	public ScheduleModel montarScheduleTarefas(Long id) {
		ScheduleModel model = new DefaultScheduleModel();
		List<Tarefa> tarefas;
		List<Status> status = new ArrayList<>(Arrays.asList(Status.values()));
		status.remove(Status.NAO_INICIADO);
		status.remove(Status.CONCLUIDO);
		try {
			if(id.longValue() == 0) {
				tarefas = this.tarefaService.buscarTarefasPorStatus(status);
			} else {
				tarefas = this.tarefaService.buscarTarefasPorColaboradorEStatus(this.colaboradorService.buscarPorId(id), status);
			}
			for (Tarefa tarefa : tarefas) {
				model.addEvent(new DefaultScheduleEvent(String.format("%s - Demanda: %s - %s",  tarefa.getColaborador().getNome(), tarefa.getNumeroDemanda(), tarefa.getTitulo()), 
						tarefa.getInicio(), 
						tarefa.getFinalPlanejado()));
			}
		} catch (ConsultaSemRetornoException e) {
			// inserirMensagemDeErro(e.getMessage());
		}
		return model;
	}
	
	public void setarObservacao(String observacao) {
		this.tarefa.setObservacao(observacao);
	} 

} // fim da classe TarefaBean
