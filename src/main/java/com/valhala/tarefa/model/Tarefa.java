package com.valhala.tarefa.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

/**
 * Entity que mapeia a tabela de Colabores no banco de dados.
 * @author Bruno Luiz Viana
 * @version 1.0
 * @since 23/02/2014
 *
 */
@Entity @Table(name="tb_tarefa")
@NamedQueries({
		@NamedQuery(name=Tarefa.NAMEDQUERY_BUSCAR_TODOS, query="select t from Tarefa t"),
		@NamedQuery(name=Tarefa.NAMEDQUERY_BUSCAR_POR_COLABORADOR, query="select t from Tarefa t where t.colaborador = :colaborador"),
		@NamedQuery(name=Tarefa.NAMEDQUERY_BUSCAR_POR_COLABORADOR_E_STATUS, query="select t from Tarefa t where t.colaborador = :colaborador and t.status in (:status)"),
		@NamedQuery(name=Tarefa.NAMEDQUERY_BUSCAR_POR_STATUS, query="select t from Tarefa t where t.status in (:status)"),
		@NamedQuery(name=Tarefa.NAMEDQUERY_BUSCAR_POR_EQUIPE, query="select t from Tarefa t where t.equipe = :equipe"),
		@NamedQuery(name=Tarefa.NAMEDQUERY_BUSCAR_POR_CLIENTE, query="select t from Tarefa t where t.cliente = :cliente"),
		@NamedQuery(name=Tarefa.NAMEDQUERY_BUSCAR_POR_SISTEMA, query="select t from Tarefa t where t.sistema = :sistema")})
public class Tarefa implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public static final String NAMEDQUERY_BUSCAR_TODOS = "buscarTodasTarefas";
	public static final String NAMEDQUERY_BUSCAR_POR_COLABORADOR = "buscarTodasTarefasPorColaborador";
	public static final String NAMEDQUERY_BUSCAR_POR_COLABORADOR_E_STATUS = "buscarTodosTarefasPorColaboradorEStatus";
	public static final String NAMEDQUERY_BUSCAR_POR_STATUS = "buscarTodasTarefasPorStatus";
	public static final String NAMEDQUERY_BUSCAR_POR_EQUIPE = "buscarTodasTarefasPorEquipe";
	public static final String NAMEDQUERY_BUSCAR_POR_CLIENTE = "buscarTodasTarefasPorCliente";
	public static final String NAMEDQUERY_BUSCAR_POR_SISTEMA = "buscarTodasTarefasPorSistema";
	
	private Long id;
	private String numeroDemanda;
	private String titulo;
	private Prioridade prioridade;
	private Date abertura;
	private Date inicio;
	private Date finalPlanejado;
	private Date finalEfetivo;
	private Status status;
	private Integer estimativa;
	private Boolean replanajado;
	private String observacao;
	private Colaborador colaborador = new Colaborador();
	private Cliente cliente = new Cliente();
	private Equipe equipe = new Equipe();
	private Sistema sistema = new Sistema();
	
	@Version
	private Long versao;
	
	public Tarefa() {
		super();
	}
	
	public Tarefa(String numeroDemanda, String titulo, Prioridade prioridade,
			Date abertura, Date inicio, Date finalPlanejado, Date finalEfetivo,
			Status status, Integer estimativa, Boolean replanajado,
			String observacao, Colaborador colaborador, Cliente cliente,
			Equipe equipe, Sistema sistema) {
		super();
		this.numeroDemanda = numeroDemanda;
		this.titulo = titulo;
		this.prioridade = prioridade;
		this.abertura = abertura;
		this.inicio = inicio;
		this.finalPlanejado = finalPlanejado;
		this.finalEfetivo = finalEfetivo;
		this.status = status;
		this.estimativa = estimativa;
		this.replanajado = replanajado;
		this.observacao = observacao;
		this.colaborador = colaborador;
		this.cliente = cliente;
		this.equipe = equipe;
		this.sistema = sistema;
	}



	public Tarefa(String numeroDemanda, String titulo,
			Prioridade prioridade) {
		super();
		this.numeroDemanda = numeroDemanda;
		this.titulo = titulo;
		this.prioridade = prioridade;
		this.colaborador = null;
		this.cliente = null;
		this.equipe = null;
		this.sistema = null;
	}

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_tarefa")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name="numero_demanda", unique=true, nullable=false)
	public String getNumeroDemanda() {
		return numeroDemanda;
	}

	public void setNumeroDemanda(String numeroDemanda) {
		this.numeroDemanda = numeroDemanda;
	}

	@Column(name="titulo_demanda", nullable=false, length=130)
	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	@Enumerated(EnumType.STRING)
	@Column(name="prioridade_demanda", nullable=false, length=80)	
	public Prioridade getPrioridade() {
		return prioridade;
	}
	
	public void setPrioridade(Prioridade prioridade) {
		this.prioridade = prioridade;
	}

	@Temporal(TemporalType.DATE)
	@Column(name="data_abertura", nullable=false)
	public Date getAbertura() {
		return abertura;
	}

	public void setAbertura(Date abertura) {
		this.abertura = abertura;
	}

	@Temporal(TemporalType.DATE)
	@Column(name="data_inicio")
	public Date getInicio() {
		return inicio;
	}

	public void setInicio(Date inicio) {
		this.inicio = inicio;
	}

	@Temporal(TemporalType.DATE)
	@Column(name="data_fim_planejado")
	public Date getFinalPlanejado() {
		return finalPlanejado;
	}

	public void setFinalPlanejado(Date finalPlanejado) {
		this.finalPlanejado = finalPlanejado;
	}

	@Temporal(TemporalType.DATE)
	@Column(name="data_fim_efetivo")
	public Date getFinalEfetivo() {
		return finalEfetivo;
	}

	public void setFinalEfetivo(Date finalEfetivo) {
		this.finalEfetivo = finalEfetivo;
	}

	@Enumerated(EnumType.STRING)
	@Column(name="status_tarefa", nullable=false, length=80)
	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	@Column(name="estimativa_tarefa")
	public Integer getEstimativa() {
		return estimativa;
	}

	public void setEstimativa(Integer estimativa) {
		this.estimativa = estimativa;
	}

	@Column(name="replanejado")
	public Boolean getReplanajado() {
		return replanajado;
	}

	public void setReplanajado(Boolean replanajado) {
		this.replanajado = replanajado;
	}

	@Lob
	@Column(name="observacao_tarefa")
	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	@ManyToOne
	public Colaborador getColaborador() {
		return colaborador;
	}

	public void setColaborador(Colaborador colaborador) {
		this.colaborador = colaborador;
	}

	@ManyToOne
	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	@ManyToOne
	public Equipe getEquipe() {
		return equipe;
	}

	public void setEquipe(Equipe equipe) {
		this.equipe = equipe;
	}
	
	@ManyToOne
	public Sistema getSistema() {
		return sistema;
	}
	
	public void setSistema(Sistema sistema) {
		this.sistema = sistema;
	}

} // fim da classe Tarefa