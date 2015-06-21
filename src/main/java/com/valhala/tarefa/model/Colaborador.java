package com.valhala.tarefa.model;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_colaborador", insertable = false, updatable = false)
    private Long id;
    @Column(name = "nome_colaborador", length = 100, unique = true, nullable = false)
    private String nome;
    @Column(name = "matricula_colaborador", length = 15, unique = true, nullable = false)
    private String matricula;
    @Column(name = "senha_colaborador", length = 255, unique = false, nullable = false)
    private String senha;

    @ElementCollection(targetClass = Atribuicao.class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<Atribuicao> atribuicoes = new HashSet<>();

    Colaborador() {
        super();
    }

    private Colaborador(Builder builder) {
        this.id = builder.id;
        this.nome = builder.nome;
        this.matricula = builder.matricula;
        this.senha = builder.senha;
        this.atribuicoes = builder.atribuicoes;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getMatricula() {
        return matricula;
    }

    public String getSenha() {
        return senha;
    }

    public Set<Atribuicao> getAtribuicoes() {
        return Collections.unmodifiableSet(this.atribuicoes);
    }

    public static class Builder {

        private Long id = null;
        private String nome;
        private String matricula;
        private String senha = "";
        private Set<Atribuicao> atribuicoes = new HashSet<>();

        public Builder(final String nome, final String matricula) {
            this.nome = nome;
            this.matricula = matricula;
        }

        public Builder id(final Long id) {
            this.id = id;
            return this;
        }

        public Builder senha(final String senha) {
            this.senha = senha;
            return this;
        }

        public Builder atribuicoes(final Set<Atribuicao> atribuicoes) {
            this.atribuicoes = atribuicoes;
            return this;
        }

        public Colaborador build() {
            return new Colaborador(this);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Colaborador)) {
            return false;
        }
        Colaborador colaborador = (Colaborador) obj;
        return colaborador.getNome().equals(this.getNome()) && colaborador.getMatricula().equals(this.getMatricula());
    }

    @Override
    public int hashCode() {
        int result = 21;
        result = 31 * result + (this.getNome() == null ? 0 : this.getNome().hashCode());
        result = 31 * result + (this.getMatricula() == null ? 0 : this.getMatricula().hashCode());
        return result;
    }

    @Override
    public String toString() {
        return this.getNome() + " - " + this.getMatricula();
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setAtribuicoes(Set<Atribuicao> atribuicoes) {
        this.atribuicoes = atribuicoes;
    }

} // fim da classe Colaborador
