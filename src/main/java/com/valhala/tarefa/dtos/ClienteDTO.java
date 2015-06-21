package com.valhala.tarefa.dtos;

import java.io.Serializable;

import com.valhala.tarefa.model.Cliente;

public class ClienteDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String nome;

    public ClienteDTO() {
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

    public static ClienteDTO fromModel(Cliente cliente) {
        ClienteDTO clienteVO = new ClienteDTO();
        clienteVO.setId(cliente.getId());
        clienteVO.setNome(cliente.getNome());
        return clienteVO;
    }

    public Cliente asModel() {
        Cliente cliente = new Cliente.Builder(this.nome).id(this.id).build();
        return cliente;
    }

}
