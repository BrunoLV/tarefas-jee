package com.valhala.tarefa.dtos;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.valhala.tarefa.model.Atribuicao;
import com.valhala.tarefa.model.Colaborador;

public class ColaboradorDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String nome;
    private String matricula;
    private String senha;

    private Set<Atribuicao> atribuicoes = new HashSet<>();

    public ColaboradorDTO() {
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

    public Set<Atribuicao> getAtribuicoes() {
        return atribuicoes;
    }

    public void setAtribuicoes(Set<Atribuicao> atribuicoes) {
        this.atribuicoes = atribuicoes;
    }

    public static ColaboradorDTO fromModel(Colaborador colaborador) {
        ColaboradorDTO colaboradorVO = new ColaboradorDTO();
        colaboradorVO.setId(colaborador.getId());
        colaboradorVO.setNome(colaborador.getNome());
        colaboradorVO.setMatricula(colaborador.getMatricula());
        colaboradorVO.setSenha(colaborador.getSenha());
        colaboradorVO.setAtribuicoes(colaborador.getAtribuicoes());
        return colaboradorVO;
    }

    public Colaborador asModel() {
        Colaborador colaborador = new Colaborador.Builder(this.nome, this.matricula).
                id(this.id != null ? this.id : null).
                senha(this.senha).
                atribuicoes(this.atribuicoes).build();
        return colaborador;
    }

}
