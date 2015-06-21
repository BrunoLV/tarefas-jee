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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_equipe")
    private Long id;
    @Column(name = "nome_equipe", unique = true, nullable = false, length = 100)
    private String nome;

    Equipe() {
        super();
    } // fim do método construtor

    private Equipe(Builder builder) {
        super();
        this.id = builder.id;
        this.nome = builder.nome;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public static class Builder {

        private Long id = null;
        private String nome;

        public Builder(final String nome) {
            this.nome = nome;
        }

        public Builder id(final Long id) {
            this.id = id;
            return this;
        }

        public Equipe build() {
            return new Equipe(this);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Equipe)) {
            return false;
        }
        Equipe equipe = (Equipe) obj;
        return equipe.getNome().equals(this.getNome());
    }

    @Override
    public int hashCode() {
        int result = 21;
        result = 31 * result + (this.getNome() == null ? 0 : this.getNome().hashCode());
        return result;
    }

    @Override
    public String toString() {
        return this.getNome();
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public void setNome(final String nome) {
        this.nome = nome;
    }

} // fim da classe Equipe
