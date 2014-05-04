package com.valhala.tarefa.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity @Table(name="tb_equipe")
@NamedQueries({
	@NamedQuery(name=Equipe.NAMEDQUERY_BUSCAR_TODOS, query="select e from Equipe e"),
	@NamedQuery(name=Equipe.NAMEDQUERY_BUSCAR_POR_NOME, query="select e from Equipe e where e.nome = :nome")
})
public class Equipe implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public static final String NAMEDQUERY_BUSCAR_TODOS = "buscarTodasEquipes";
	public static final String NAMEDQUERY_BUSCAR_POR_NOME = "buscarEquipePorNome";
	
	private Long id;
	private String nome;
	
	public Equipe() {
		super();
	}
	
	public Equipe(String nome) {
		super();
		this.nome = nome;
	}

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_equipe")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name="nome_equipe", unique=true, nullable=false, length=100)
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}
