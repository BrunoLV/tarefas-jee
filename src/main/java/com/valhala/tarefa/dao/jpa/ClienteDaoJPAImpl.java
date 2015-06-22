package com.valhala.tarefa.dao.jpa;

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
class ClienteDaoJPAImpl extends BaseJPADao<Cliente> implements ClienteDao {

    public ClienteDaoJPAImpl() {
        this.classePersistente = Cliente.class;
    } // fim do método construtor

    @Override
    public List<Cliente> listarTudo() {
        TypedQuery<Cliente> query = construirTypedQuery(Cliente.NAMEDQUERY_BUSCAR_TODOS);
        List<Cliente> clientes = query.getResultList();
        return clientes;
    } // fim do método listaTudo

}// fim da classe ClienteDaoImpl
