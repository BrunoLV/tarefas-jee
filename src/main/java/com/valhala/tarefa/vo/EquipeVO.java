package com.valhala.tarefa.vo;

import java.io.Serializable;

import com.valhala.tarefa.model.Equipe;

public class EquipeVO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long id;
    private String nome;

    public EquipeVO() {
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

	public static EquipeVO fromModel(Equipe equipe) {
		EquipeVO equipeVO = new EquipeVO();
		equipeVO.setId(equipe.getId());
		equipeVO.setNome(equipe.getNome());
		return equipeVO;
	}

	public Equipe asModel() {
		Equipe equipe = new Equipe.Builder(this.nome).id(this.id).build();
		return equipe;
	}

}
