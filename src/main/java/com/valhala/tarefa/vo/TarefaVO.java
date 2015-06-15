package com.valhala.tarefa.vo;

import java.io.Serializable;
import java.util.Date;

import com.valhala.tarefa.model.Prioridade;
import com.valhala.tarefa.model.Status;
import com.valhala.tarefa.model.StatusSla;
import com.valhala.tarefa.model.Tarefa;
import com.valhala.tarefa.model.TipoDemanda;

public class TarefaVO implements Serializable {

	private static final long serialVersionUID = 1L;
	
    private Long id;
    private Long numeroDemanda;
    private String titulo;
    private Prioridade prioridade;
    private String categoria;
    private TipoDemanda tipoDemanda;
    private Date abertura;
    private Date inicio;
    private Date desenvolvimento;
    private Date homologacao;
    private Date finalPlanejado;
    private Date finalEfetivo;
    private Status status;	
    private StatusSla statusSla;
    private String observacao;
    private Long diasAbertura;
    private ColaboradorVO colaborador = new ColaboradorVO();
    private ClienteVO cliente = new ClienteVO();
    private EquipeVO equipe = new EquipeVO();
    private SistemaVO sistema = new SistemaVO();
	
	public TarefaVO() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getNumeroDemanda() {
		return numeroDemanda;
	}

	public void setNumeroDemanda(Long numeroDemanda) {
		this.numeroDemanda = numeroDemanda;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Prioridade getPrioridade() {
		return prioridade;
	}

	public void setPrioridade(Prioridade prioridade) {
		this.prioridade = prioridade;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public TipoDemanda getTipoDemanda() {
		return tipoDemanda;
	}

	public void setTipoDemanda(TipoDemanda tipoDemanda) {
		this.tipoDemanda = tipoDemanda;
	}

	public Date getAbertura() {
		return abertura;
	}

	public void setAbertura(Date abertura) {
		this.abertura = abertura;
	}

	public Date getInicio() {
		return inicio;
	}

	public void setInicio(Date inicio) {
		this.inicio = inicio;
	}

	public Date getDesenvolvimento() {
		return desenvolvimento;
	}

	public void setDesenvolvimento(Date desenvolvimento) {
		this.desenvolvimento = desenvolvimento;
	}

	public Date getHomologacao() {
		return homologacao;
	}

	public void setHomologacao(Date homologacao) {
		this.homologacao = homologacao;
	}

	public Date getFinalPlanejado() {
		return finalPlanejado;
	}

	public void setFinalPlanejado(Date finalPlanejado) {
		this.finalPlanejado = finalPlanejado;
	}

	public Date getFinalEfetivo() {
		return finalEfetivo;
	}

	public void setFinalEfetivo(Date finalEfetivo) {
		this.finalEfetivo = finalEfetivo;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public StatusSla getStatusSla() {
		return statusSla;
	}

	public void setStatusSla(StatusSla statusSla) {
		this.statusSla = statusSla;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public ColaboradorVO getColaborador() {
		return colaborador;
	}
	
	public Long getDiasAbertura() {
		return diasAbertura;
	}
	
	private void setDiasAbertura(Long diasAbertura) {
		this.diasAbertura = diasAbertura;
	}

	public void setColaborador(ColaboradorVO colaborador) {
		this.colaborador = colaborador;
	}

	public ClienteVO getCliente() {
		return cliente;
	}

	public void setCliente(ClienteVO cliente) {
		this.cliente = cliente;
	}

	public EquipeVO getEquipe() {
		return equipe;
	}

	public void setEquipe(EquipeVO equipe) {
		this.equipe = equipe;
	}

	public SistemaVO getSistema() {
		return sistema;
	}

	public void setSistema(SistemaVO sistema) {
		this.sistema = sistema;
	}
	
	public final Tarefa asModel() {
		Tarefa tarefa = new Tarefa.Builder(this.numeroDemanda, this.titulo, this.prioridade, this.tipoDemanda).
				id(this.id).
				abertura(this.abertura).
				inicio(this.inicio).
				desenvolvimento(this.desenvolvimento).
				homologacao(this.homologacao).
				finalPlanejado(this.finalPlanejado).
				finalEfetivo(this.finalEfetivo).
				status(this.status).
				statusSla(this.statusSla).
				categoria(this.categoria).
				observacao(this.observacao).
				cliente(this.cliente.asModel()).
				sistema(this.sistema.asModel()).
				equipe(this.equipe.asModel()).
				colaborador(this.colaborador.asModel()).build();
		return tarefa;
	}
	
	public static TarefaVO fromModel(final Tarefa tarefa) {
		TarefaVO tarefaVO = new TarefaVO();
    	tarefaVO.setId(tarefa.getId());
    	tarefaVO.setNumeroDemanda(tarefa.getNumeroDemanda());
    	tarefaVO.setTitulo(tarefa.getTitulo());
    	tarefaVO.setPrioridade(tarefa.getPrioridade());
    	tarefaVO.setTipoDemanda(tarefa.getTipoDemanda());
    	tarefaVO.setCategoria(tarefa.getCategoria());
    	tarefaVO.setAbertura(tarefa.getAbertura());
    	tarefaVO.setInicio(tarefa.getInicio());
    	tarefaVO.setDesenvolvimento(tarefa.getDesenvolvimento());
    	tarefaVO.setHomologacao(tarefa.getHomologacao());
    	tarefaVO.setFinalPlanejado(tarefa.getFinalPlanejado());
    	tarefaVO.setFinalEfetivo(tarefa.getFinalEfetivo());
    	tarefaVO.setStatus(tarefa.getStatus());
    	tarefaVO.setStatusSla(tarefa.getStatusSla());
    	tarefaVO.setObservacao(tarefa.getObservacao());
    	tarefaVO.setDiasAbertura(tarefa.retornarQuantidadeDeDiasAberto());
    	tarefaVO.setColaborador(tarefa.getColaborador() != null ? ColaboradorVO.fromModel(tarefa.getColaborador()) : new ColaboradorVO());
    	tarefaVO.setCliente(tarefa.getCliente() != null ? ClienteVO.fromModel(tarefa.getCliente()) : new ClienteVO());
    	tarefaVO.setEquipe(tarefa.getEquipe() != null ? EquipeVO.fromModel(tarefa.getEquipe()) : new EquipeVO());
    	tarefaVO.setSistema(tarefa.getSistema() != null ? SistemaVO.fromModel(tarefa.getSistema()) : new SistemaVO());
		return tarefaVO;
	}

}
