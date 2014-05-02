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

@Entity @Table(name="tb_cliente")
@NamedQueries({
	@NamedQuery(name=Cliente.NAMEDQUERY_BUSCAR_TODOS, query="select c from Cliente c")
})
public class Cliente implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public static final String NAMEDQUERY_BUSCAR_TODOS = "buscarTodasClientes";
	
	private Long id;
	private String nome;
	
	public Cliente() {
		super();
	}

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_cliente")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name="nome_cliente", unique=true, nullable=false, length=100)
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}
