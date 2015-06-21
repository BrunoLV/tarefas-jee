package com.valhala.tarefa.model;

import javax.persistence.*;

import java.io.Serializable;

/**
 * Classe que mapeia a tabela de sistemas dentro da aplicação.
 *
 * @author Bruno Luiz Viana
 * @version 1.0
 * @since 23/06/2014
 */
@Entity
@Table(name = "tb_sistema")
@NamedQueries({
    @NamedQuery(name = Sistema.NAMEDQUERY_BUSCAR_TODOS, query = "select s from Sistema s")
})
public class Sistema implements Serializable {

    public static final String NAMEDQUERY_BUSCAR_TODOS = "buscarTodosSistemas";
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sistema")
    private Long id;
    @Column(name = "nome_sistema", nullable = false, unique = true, length = 100)
    private String nome;

    Sistema() {
        super();
    } // fim do método construtor

    private Sistema(Builder builder) {
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

        public Sistema build() {
            return new Sistema(this);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Sistema)) {
            return false;
        }
        Sistema sistema = (Sistema) obj;
        return sistema.getNome().equals(this.getNome());
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

    public void setId(Long id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

} // fim da classe Sistema
