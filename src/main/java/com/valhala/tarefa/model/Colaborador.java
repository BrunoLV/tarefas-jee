package com.valhala.tarefa.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
import javax.persistence.Version;

/**
 * Entity que mapeia a tabela de Colaboradores no banco de dados.
 * @author Bruno Luiz Viana
 * @version 1.0
 * @since 23/02/2014
 *
 */
@Entity
@NamedQueries({
	@NamedQuery(name=Colaborador.NAMEDQUERY_BUSCAR_TODOS, query="select c from Colaborador c"),
	@NamedQuery(name=Colaborador.NAMEDQUERY_BUSCAR_POR_MATRICULA, query="select c from Colaborador c where c.matricula = :matricula")})
public class Colaborador implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public static final String NAMEDQUERY_BUSCAR_TODOS = "buscarTodosColaboradores";
	public static final String NAMEDQUERY_BUSCAR_POR_MATRICULA = "buscarPorMatricula";
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private String matricula;
	private String senha;
	
	@ElementCollection(targetClass = Atribuicao.class, fetch = FetchType.EAGER)
	@Enumerated(EnumType.STRING)
	private List<Atribuicao> atribuicoes = new ArrayList<>();
	
	@Version
	private Long versao;
	
	public Colaborador() {
	}
	
	public Colaborador(Long id, String nome, String matricula, String senha) {
		super();
		this.id = id;
		this.nome = nome;
		this.matricula = matricula;
		this.senha = senha;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public Long getVersao() {
		return versao;
	}
	
	public void setVersao(Long versao) {
		this.versao = versao;
	}
	
	public List<Atribuicao> getAtribuicoes() {
		return atribuicoes;
	}
	
	public void setAtribuicoes(List<Atribuicao> atribuicoes) {
		this.atribuicoes = atribuicoes;
	}

	@Override
	public String toString() {
		return "Colaborador [id=" + id + ", nome=" + nome + ", matricula="
				+ matricula + ", senha=" + senha + "]";
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
		Colaborador other = (Colaborador) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
} // fim da classe Colaborador
