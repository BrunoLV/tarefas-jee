package com.valhala.tarefa.ejb;

import com.valhala.tarefa.dao.api.ClienteDao;
import com.valhala.tarefa.model.Cliente;
import com.valhala.tarefa.qualifiers.Auditavel;
import java.io.Serializable;

import javax.ejb.*;
import javax.inject.Inject;
import java.util.List;

/**
 * EJB responsavel pela regra de negócio relacionada a Cliente.
 *
 * @author Bruno Luiz Viana
 * @version 1.0
 * @since 04/06/2014
 */
@Auditavel
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ClienteEJB {

    @Inject
    private ClienteDao clienteDao;

    @Auditavel
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Cliente buscarPorId(Serializable id) {
        return this.clienteDao.buscarPorId(id);
    }

    /**
     * Método utilizado para retorna a lista de clientes registrados no sistema.
     *
     * @return
     */
    @Auditavel
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Cliente> buscarTodosClientes() {
        return this.clienteDao.listarTudo();
    } // dim do método buscarTodosClientes

} // fim da classe ClienteService
