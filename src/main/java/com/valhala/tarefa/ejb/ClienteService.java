package com.valhala.tarefa.ejb;

import com.valhala.tarefa.dao.api.ClienteDao;
import com.valhala.tarefa.exceptions.ConsultaSemRetornoException;
import com.valhala.tarefa.model.Cliente;
import com.valhala.tarefa.qualifiers.Auditavel;

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
public class ClienteService {

    @Inject
    private ClienteDao clienteDao;

    /**
     * Método utilizado para retorna a lista de clientes registrados no sistema.
     *
     * @return
     * @throws ConsultaSemRetornoException
     */
    @Auditavel
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Cliente> buscarTodosClientes() throws ConsultaSemRetornoException {
        return this.clienteDao.listarTudo();
    } // dim do método buscarTodosClientes

} // fim da classe ClienteService