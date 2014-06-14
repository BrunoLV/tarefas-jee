package com.valhala.tarefa.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity que mapeia a tabela de Colaboradores no banco de dados.
 *
 * @author Bruno Luiz Viana
 * @version 1.0
 * @since 23/02/2014
 */
@Entity
@Table(name = "tb_colaborador")
@NamedQueries({
        @NamedQuery(name = Colaborador.NAMEDQUERY_BUSCAR_TODOS, query = "select c from Colaborador c"),
        @NamedQuery(name = Colaborador.NAMEDQUERY_BUSCAR_POR_MATRICULA, query = "select c from Colaborador c where c.matricula = :matricula"),
        @NamedQuery(name = Colaborador.NAMEDQUERY_BUSCAR_POR_NOME, query = "select c from Colaborador c where c.nome = :nome")})
public class Colaborador implements Serializable {

    public static final String NAMEDQUERY_BUSCAR_TODOS = "buscarTodosColaboradores";
    public static final String NAMEDQUERY_BUSCAR_POR_MATRICULA = "buscarColaboradorPorMatricula";
    public static final String NAMEDQUERY_BUSCAR_POR_NOME = "buscarColaboradorPorNome";
    private static final long serialVersionUID = 1L;
    private Long id;
    private String nome;
    private String matricula;
    private String senha;
    private List<Atribuicao> atribuicoes = new ArrayList<>();

    public Colaborador() {
        super();
    }

    public Colaborador(Long id, String nome, String matricula, String senha) {
        super();
        this.id = id;
        this.nome = nome;
        this.matricula = matricula;
        this.senha = senha;
    }

    public Colaborador(Long id, String nome, String matricula, String senha, List<Atribuicao> atribuicoes) {
        super();
        this.id = id;
        this.nome = nome;
        this.matricula = matricula;
        this.senha = senha;
        this.atribuicoes = atribuicoes;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_colaborador")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "nome_colaborador", length = 100, unique = true, nullable = false)
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Column(name = "matricula_colaborador", length = 15, unique = true, nullable = false)
    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    @Column(name = "senha_colaborador", length = 255, unique = true, nullable = false)
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @ElementCollection(targetClass = Atribuicao.class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    public List<Atribuicao> getAtribuicoes() {
        return atribuicoes;
    }

    public void setAtribuicoes(List<Atribuicao> atribuicoes) {
        this.atribuicoes = atribuicoes;
    }

} // fim da classe Colaborador
