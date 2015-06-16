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

import com.valhala.tarefa.ejb.ClienteBean;
import com.valhala.tarefa.ejb.ColaboradorBean;
import com.valhala.tarefa.ejb.EquipeBean;
import com.valhala.tarefa.ejb.SistemaBean;
import com.valhala.tarefa.ejb.TarefaBean;
import com.valhala.tarefa.exceptions.ConsultaSemRetornoException;
import com.valhala.tarefa.exceptions.ServiceException;
import com.valhala.tarefa.model.Cliente;
import com.valhala.tarefa.model.Colaborador;
import com.valhala.tarefa.model.Equipe;
import com.valhala.tarefa.model.Prioridade;
import com.valhala.tarefa.model.Sistema;
import com.valhala.tarefa.model.Status;
import com.valhala.tarefa.model.StatusSla;
import com.valhala.tarefa.model.Tarefa;
import com.valhala.tarefa.model.TipoDemanda;
import com.valhala.tarefa.vo.ClienteVO;
import com.valhala.tarefa.vo.ColaboradorVO;
import com.valhala.tarefa.vo.EquipeVO;
import com.valhala.tarefa.vo.SistemaVO;
import com.valhala.tarefa.vo.TarefaVO;

/**
 * Managed Bean utilizado para as ações de tela relacionadas a tarefas.
 *
 * @author Bruno Luiz Viana
 * @version 1.0
 * @since 23/02/2014
 */
@Named("TarefaMB")
@RequestScoped
public class TarefaMB extends BaseMB implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final String CHAVE_OBJETO_EDICAO = "TAREFA_EDICAO";

    private static final String OUTCOME_ENVIAR_EDICAO = "/pages/manter/cadastro-tarefas.xhtml?faces-redirect=true";
    
    @EJB private TarefaBean tarefaService;
    @EJB private ClienteBean clienteService;
    @EJB private SistemaBean sistemaService;
    @EJB private EquipeBean equipeService;
    @EJB private ColaboradorBean colaboradorService;

    private TarefaVO tarefa;

    private Long idConsultaSchedule;

    public TarefaMB() {
    	super();
    }

    public TarefaVO getTarefa() {
        return tarefa;
    }

    public void setTarefa(TarefaVO tarefa) {
        this.tarefa = tarefa;
    }

    public Long getIdConsultaSchedule() {
        return idConsultaSchedule;
    }

    public void setIdConsultaSchedule(Long idConsultaSchedule) {
        this.idConsultaSchedule = idConsultaSchedule;
    }

    @PostConstruct
    public void inicializarBean() {
        if (obterObjectDaSession(TarefaMB.CHAVE_OBJETO_EDICAO) != null) {
            this.tarefa = (TarefaVO) obterObjectDaSession(TarefaMB.CHAVE_OBJETO_EDICAO);
			removerObjetoDaSession(TarefaMB.CHAVE_OBJETO_EDICAO);
        } else {
            this.tarefa = new TarefaVO();
        } // fim do bloco if/else
    } // fim do método

    public List<Tarefa> listarPorColaborador(ColaboradorVO colaborador) {
        List<Tarefa> tarefas;
        List<Status> status = new ArrayList<>(Arrays.asList(Status.values()));
		status.remove(Status.CONCLUIDO);
		tarefas = this.tarefaService.buscarTarefasPorColaboradorEStatus(colaborador.asModel(), status);
        return tarefas;
    }

    public List<Tarefa> buscarTodosConcluidos() {
        List<Tarefa> tarefas;
        List<Status> status = new ArrayList<>();
        status.add(Status.CONCLUIDO);
        tarefas = this.tarefaService.buscarTarefasPorStatus(status);
        return tarefas;
    }

    public Prioridade[] getPrioridades() {
        return Prioridade.values();
    }

    public Status[] getStatus() {
        return Status.values();
    }
    
    public StatusSla[] getStatusSla() {
    	return StatusSla.values();
    }

    public TipoDemanda[] getTiposDemanda() {
        return TipoDemanda.values();
    }

    public void salvarTarefa() {
        tarefa.setFinalEfetivo(tarefa.getStatus().equals(Status.CONCLUIDO) ? new Date() : null);
        try {
            if (tarefa.getId() != null && tarefa.getId() > 0) {
            	this.tarefaService.editarTarefa(tarefa.asModel());
                inserirMensagemDeSucesso("Registro atualizado com sucesso.");
            } else {
            	tarefa.setId(null);
                this.tarefaService.cadastrarTarefa(tarefa.asModel());
                tarefa = new TarefaVO();
                inserirMensagemDeSucesso("Registro inserido com sucesso.");
            }
        } catch (ServiceException e) {
            inserirMensagemDeErro(e.getMessage());
        }
    }

    public void removerTarefa(Serializable id) {
        this.tarefaService.removerTarefa(id);
		inserirMensagemDeSucesso("Registro removido com sucesso.");
    }

    public String enviarTarefaParaEdicao(Serializable id) {
        String outcome = null;
        TarefaVO tarefa = TarefaVO.fromModel(this.tarefaService.buscarPorId(id));
        //inserirObjetoNoFlashScope(TarefaBean.CHAVE_OBJETO_EDICAO, tarefa);
		inserirObjetoNaSession(TarefaMB.CHAVE_OBJETO_EDICAO, tarefa);
		outcome = TarefaMB.OUTCOME_ENVIAR_EDICAO;
        return outcome;
    }

    public String enviarTarefaParaAjusteAdministrativo(Serializable id) {
        String outcome = null;
        TarefaVO tarefa = TarefaVO.fromModel(this.tarefaService.buscarPorId(id));
		//inserirObjetoNoFlashScope(TarefaBean.CHAVE_OBJETO_EDICAO, tarefa);
		inserirObjetoNaSession(TarefaMB.CHAVE_OBJETO_EDICAO, tarefa);
		outcome = TarefaMB.OUTCOME_ENVIAR_EDICAO;
        return outcome;
    }

    public List<SistemaVO> getSistemas() {
        List<SistemaVO> sistemas = new ArrayList<>();
        List<Sistema> auxiliarList = this.sistemaService.buscarTodosSistemas();
        for (Sistema sistema : auxiliarList) {
			sistemas.add(SistemaVO.fromModel(sistema));
		}
        return sistemas;
    }

    public List<ClienteVO> getClientes() {
        List<ClienteVO> clientes = new ArrayList<>();
        List<Cliente> auxiliarList = this.clienteService.buscarTodosClientes();
        for (Cliente cliente : auxiliarList) {
			clientes.add(ClienteVO.fromModel(cliente));
		}
        return clientes;
    }

    public List<EquipeVO> getEquipes() {
        List<EquipeVO> equipes = new ArrayList<>();
        List<Equipe> auxiliarList = this.equipeService.buscarTodasEquipes();
        for (Equipe equipe : auxiliarList) {
			equipes.add(EquipeVO.fromModel(equipe));
		}
        return equipes;
    }

    public List<ColaboradorVO> getColaboradores() {
        List<ColaboradorVO> colaboradores = new ArrayList<>();
        List<Colaborador> auxiliarList = this.colaboradorService.buscarTodosColaboradores();
        for (Colaborador colaborador : auxiliarList) {
			colaboradores.add(ColaboradorVO.fromModel(colaborador));
		}
        return colaboradores;
    }

    public List<Tarefa> getTarefasNaoConcluidas() throws ConsultaSemRetornoException {
        List<Tarefa> tarefas;
        List<Status> status = new ArrayList<>(Arrays.asList(Status.values()));
        status.remove(Status.CONCLUIDO);
        tarefas = this.tarefaService.buscarTarefasPorStatus(status);
        return tarefas;
    }

    public void setarObservacao(String observacao) {
        this.tarefa.setObservacao(observacao);
    } // fim do método setarObservação

} // fim da classe TarefaBean