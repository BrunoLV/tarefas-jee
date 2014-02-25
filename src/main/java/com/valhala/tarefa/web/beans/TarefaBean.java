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

import com.valhala.tarefa.ejb.TarefaService;
import com.valhala.tarefa.exceptions.ConsultaSemRetornoException;
import com.valhala.tarefa.exceptions.CopiaDePropriedadesException;
import com.valhala.tarefa.exceptions.ServiceException;
import com.valhala.tarefa.model.Colaborador;
import com.valhala.tarefa.model.Prioridade;
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

	private static final String OUTCOME_ENVIAR_EDICAO = "/pages/manter/cadastro-tarefas.xhtml?faces-redirect=true";
	private static final String OUTCOME_ENVIAR_AJUSTE = "/pages/lider/ajuste-tarefas-administrativo.xhtml?faces-redirect=true";

	@EJB
	private TarefaService tarefaService;

	private Tarefa tarefa;
	private List<Tarefa> tarefas;

	public TarefaBean() {
	}

	public Tarefa getTarefa() {
		return tarefa;
	}

	public void setTarefa(Tarefa tarefa) {
		this.tarefa = tarefa;
	}

	public List<Tarefa> getTarefas() {
		return tarefas;
	}

	public void setTarefas(List<Tarefa> tarefas) {
		this.tarefas = tarefas;
	}

	@PostConstruct
	public void inicializarBean() {
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
			System.out.println(e.getMessage());
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
			System.out.println(e.getMessage());
			inserirMensagemDeErro(e.getMessage());
		} 
		return outcome;
	}
	
	public void setarObservacao(String observacao) {
		this.tarefa.setObservacao(observacao);
	} 

} // fim da classe TarefaBean
