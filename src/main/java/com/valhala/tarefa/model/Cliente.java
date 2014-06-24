package com.valhala.tarefa.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Classe que mapeia a tabela de Clientes no banco de dados.
 *
 * @author Bruno Luiz Viana
 * @version 1.0
 * @since 23/06/2014
 */
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
    } // fim do método construtor

    public Cliente(String nome) {
        super();
        this.nome = nome;
    } // fim do método construtor

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

} // fim da classe Cliente