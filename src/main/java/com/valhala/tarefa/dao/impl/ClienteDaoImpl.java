package com.valhala.tarefa.dao.impl;

import java.util.List;

import javax.persistence.TypedQuery;

import com.valhala.tarefa.dao.api.ClienteDao;
import com.valhala.tarefa.model.Cliente;

/**
 * Classe que implementa ClienteDao para
 *
 * @author Bruno Luiz Viana
 * @version 1.0
 * @since 23/06/2014
 */
public class ClienteDaoImpl extends BaseDao<Cliente> implements ClienteDao {

    public static final Class<Cliente> CLASSE_PERSISTENTE = Cliente.class;

    public ClienteDaoImpl() {
        this.classePersistente = ClienteDaoImpl.CLASSE_PERSISTENTE;
    } // fim do método construtor

    @Override
    public List<Cliente> listarTudo() {
        TypedQuery<Cliente> query = this.entityManager.createNamedQuery(Cliente.NAMEDQUERY_BUSCAR_TODOS, ClienteDaoImpl.CLASSE_PERSISTENTE);
        List<Cliente> clientes = query.getResultList();
        return clientes;
    } // fim do método listaTudo

}// fim da classe ClienteDaoImpl
