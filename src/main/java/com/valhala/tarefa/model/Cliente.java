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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente", insertable = false, updatable = false)
    private Long id;
    @Column(name = "nome_cliente", unique = true, nullable = false, length = 100)
    private String nome;

    /**
     * Construtor default privado de pacote. Existe para atender exigencia do
     * framework de persistencia.
     */
    Cliente() {
        super();
    } // fim do método construtor default

    private Cliente(Builder builder) {
        this.id = builder.id;
        this.nome = builder.nome;
    } // fim da metodo construtor

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

        public Cliente build() {
            return new Cliente(this);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Cliente)) {
            return false;
        }
        Cliente cliente = (Cliente) obj;
        return cliente.getNome().equals(this.getNome());
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

} // fim da classe Cliente
