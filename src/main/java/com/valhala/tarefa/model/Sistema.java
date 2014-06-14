package com.valhala.tarefa.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tb_sistema")
@NamedQueries({
        @NamedQuery(name = Sistema.NAMEDQUERY_BUSCAR_TODOS, query = "select s from Sistema s")
})
public class Sistema implements Serializable {

    public static final String NAMEDQUERY_BUSCAR_TODOS = "buscarTodosSistemas";
    private static final long serialVersionUID = 1L;
    private Long id;
    private String nome;

    public Sistema() {
        super();
    }

    public Sistema(String nome) {
        super();
        this.nome = nome;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sistema")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "nome_sistema", nullable = false, unique = true, length = 100)
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

}
