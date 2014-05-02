package com.valhala.tarefa.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 * Entity que mapeia a tabela de Colaboradores no banco de dados.
 * @author Bruno Luiz Viana
 * @version 1.0
 * @since 23/02/2014
 *
 */
@Entity @Table(name="tb_colaborador")
@NamedQueries({
	@NamedQuery(name=Colaborador.NAMEDQUERY_BUSCAR_TODOS, query="select c from Colaborador c"),
	@NamedQuery(name=Colaborador.NAMEDQUERY_BUSCAR_POR_MATRICULA, query="select c from Colaborador c where c.matricula = :matricula")})
public class Colaborador implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public static final String NAMEDQUERY_BUSCAR_TODOS = "buscarTodosColaboradores";
	public static final String NAMEDQUERY_BUSCAR_POR_MATRICULA = "buscarPorMatricula";
	
	private Long id;
	private String nome;
	private String matricula;
	private String senha;
	private List<Atribuicao> atribuicoes = new ArrayList<>();
	
	public Colaborador() {
		super();
	}
	
	public Colaborador(Long id, String nome, String matricula, String senha) {
		super();
		this.id = id;
		this.nome = nome;
		this.matricula = matricula;
		this.senha = senha;
	}

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_colaborador")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name="nome_colaborador", length=100, nullable=false)
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Column(name="matricula_colaborador", length=15, unique=true, nullable=false)
	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	@Column(name="senha_colaborador", length=255, unique=true, nullable=false)
	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	@ElementCollection(targetClass = Atribuicao.class, fetch = FetchType.EAGER)
	@Enumerated(EnumType.STRING)
	public List<Atribuicao> getAtribuicoes() {
		return atribuicoes;
	}
	
	public void setAtribuicoes(List<Atribuicao> atribuicoes) {
		this.atribuicoes = atribuicoes;
	}

} // fim da classe Colaborador
