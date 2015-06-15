package com.valhala.tarefa.vo;

import java.io.Serializable;

import com.valhala.tarefa.model.Sistema;

public class SistemaVO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
    private String nome;
    
    public SistemaVO() {
    	super();
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

	public static SistemaVO fromModel(Sistema sistema) {
		SistemaVO sistemaVO = new SistemaVO();
		sistemaVO.setId(sistema.getId());
		sistemaVO.setNome(sistema.getNome());
		return sistemaVO;
	}

	public Sistema asModel() {
		Sistema sistema = new Sistema.Builder(this.nome).id(this.id).build();
		return sistema;
	}

}
