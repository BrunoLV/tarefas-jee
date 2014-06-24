package com.valhala.tarefa.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Classe que mapeia a tabela de equipes dentro da aplicação.
 *
 * @author Bruno Luiz Viana
 * @version 1.0
 * @since 23/06/2014
 */
@Entity
@Table(name = "tb_equipe")
@NamedQueries({
        @NamedQuery(name = Equipe.NAMEDQUERY_BUSCAR_TODOS, query = "select e from Equipe e"),
        @NamedQuery(name = Equipe.NAMEDQUERY_BUSCAR_POR_NOME, query = "select e from Equipe e where e.nome = :nome")
})
public class Equipe implements Serializable {

    public static final String NAMEDQUERY_BUSCAR_TODOS = "buscarTodasEquipes";
    public static final String NAMEDQUERY_BUSCAR_POR_NOME = "buscarEquipePorNome";
    private static final long serialVersionUID = 1L;
    private Long id;
    private String nome;

    public Equipe() {
        super();
    } // fim do método construtor

    public Equipe(String nome) {
        super();
        this.nome = nome;
    } // fim do método construtor

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_equipe")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "nome_equipe", unique = true, nullable = false, length = 100)
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

} // fim da classe Equipe