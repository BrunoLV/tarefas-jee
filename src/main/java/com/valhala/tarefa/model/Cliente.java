package com.valhala.tarefa.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tb_cliente")
@NamedQueries({
        @NamedQuery(name = Cliente.NAMEDQUERY_BUSCAR_TODOS, query = "select c from Cliente c")
})
public class Cliente implements Serializable {

    public static final String NAMEDQUERY_BUSCAR_TODOS = "buscarTodasClientes";
    private static final long serialVersionUID = 1L;
    private Long id;
    private String nome;

    public Cliente() {
        super();
    }

    public Cliente(String nome) {
        super();
        this.nome = nome;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "nome_cliente", unique = true, nullable = false, length = 100)
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

}
