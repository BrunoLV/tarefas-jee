package com.valhala.tarefa.dtos;

import java.io.Serializable;

import com.valhala.tarefa.model.Sistema;

public class SistemaDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String nome;

    public SistemaDTO() {
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

    public static SistemaDTO fromModel(Sistema sistema) {
        SistemaDTO sistemaVO = new SistemaDTO();
        sistemaVO.setId(sistema.getId());
        sistemaVO.setNome(sistema.getNome());
        return sistemaVO;
    }

    public Sistema asModel() {
        Sistema sistema = new Sistema.Builder(this.nome).id(this.id).build();
        return sistema;
    }

}
