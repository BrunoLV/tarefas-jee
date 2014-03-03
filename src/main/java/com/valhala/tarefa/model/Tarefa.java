package com.valhala.tarefa.model;

import java.io.Serializable;
import java.util.Date;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

/**
 * Entity que mapeia a tabela de Colabores no banco de dados.
 * @author Bruno Luiz Viana
 * @version 1.0
 * @since 23/02/2014
 *
 */
@Entity @Audited
@NamedQueries({
		@NamedQuery(name=Tarefa.NAMEDQUERY_BUSCAR_TODOS, query="select t from Tarefa t"),
		@NamedQuery(name=Tarefa.NAMEDQUERY_BUSCAR_POR_COLABORADOR, query="select t from Tarefa t where t.colaborador = :colaborador"),
		@NamedQuery(name=Tarefa.NAMEDQUERY_BUSCAR_POR_COLABORADOR_E_STATUS, query="select t from Tarefa t where t.colaborador = :colaborador and t.status in (:status)"),
		@NamedQuery(name=Tarefa.NAMEDQUERY_BUSCAR_POR_STATUS, query="select t from Tarefa t where t.status in (:status)")})
public class Tarefa implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public static final String NAMEDQUERY_BUSCAR_TODOS = "buscarTodasTarefas";
	public static final String NAMEDQUERY_BUSCAR_POR_COLABORADOR = "buscarTodasTarefasPorColaborador";
	public static final String NAMEDQUERY_BUSCAR_POR_COLABORADOR_E_STATUS = "buscarTodosTarefasPorColaboradorEStatus";
	public static final String NAMEDQUERY_BUSCAR_POR_STATUS = "buscarTodasTarefasPorStatus";
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String numeroDemanda;
	private String titulo;
	private String areaSolicitante;
	@Enumerated(EnumType.STRING)
	private Prioridade prioridade;
	@Temporal(TemporalType.DATE)
	private Date inicio;
	@Temporal(TemporalType.DATE)
	private Date finalPlanejado;
	@Temporal(TemporalType.DATE)
	private Date finalEfetivo;
	@Enumerated(EnumType.STRING)
	private Status status;
	private Integer totalHoras;
	@Lob
	private String observacao;
	
	@ManyToOne @Audited(targetAuditMode=RelationTargetAuditMode.NOT_AUDITED)
	private Colaborador colaborador = new Colaborador();
	
	@Version
	private Long versao;
	
	public Tarefa() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumeroDemanda() {
		return numeroDemanda;
	}

	public void setNumeroDemanda(String numeroDemanda) {
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

	public Date getInicio() {
		return inicio;
	}

	public void setInicio(Date inicio) {
		this.inicio = inicio;
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

	public Integer getTotalHoras() {
		return totalHoras;
	}

	public void setTotalHoras(Integer totalHoras) {
		this.totalHoras = totalHoras;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	
	public Colaborador getColaborador() {
		return colaborador;
	}
	
	public void setColaborador(Colaborador colaborador) {
		this.colaborador = colaborador;
	}
	
	public Long getVersao() {
		return versao;
	}
	
	public void setVersao(Long versao) {
		this.versao = versao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tarefa other = (Tarefa) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String
				.format("Tarefa [id=%s, numeroDemanda=%s, titulo=%s, prioridade=%s, inicio=%s, finalPlanejado=%s, finalEfetivo=%s, status=%s, totalHoras=%s, observacao=%s, colaborador=%s]",
						id, numeroDemanda, titulo, prioridade, inicio,
						finalPlanejado, finalEfetivo, status,
						totalHoras, observacao, colaborador);
	}

	public String getAreaSolicitante() {
		return areaSolicitante;
	}

	public void setAreaSolicitante(String areaSolicitante) {
		this.areaSolicitante = areaSolicitante;
	}

} // fim da classe Tarefa