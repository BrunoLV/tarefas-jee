package com.valhala.tarefa.ejb;

import com.valhala.tarefa.dao.api.SistemaDao;
import com.valhala.tarefa.exceptions.ConsultaSemRetornoException;
import com.valhala.tarefa.model.Sistema;
import com.valhala.tarefa.qualifiers.Auditavel;
import java.io.Serializable;

import javax.ejb.*;
import javax.inject.Inject;
import java.util.List;

/**
 * EJB responsavel pela regra de negocio relacionada a Sistema.
 *
 * @author Bruno Luiz Viana
 * @version 1.1
 * @since 04/06/2014
 */
@Auditavel
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class SistemaEJB {

    @Inject
    private SistemaDao sistemaDao;

    @Auditavel
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Sistema buscarPorId(Serializable id) {
        return this.sistemaDao.buscarPorId(id);
    }

    /**
     * Método utilizado para retornar a lista de todos sistemas cadastrados na
     * aplicação.
     *
     * @return
     */
    @Auditavel
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Sistema> buscarTodosSistemas() {
        return this.sistemaDao.listarTudo();
    } // fim do buscarTodosSistemas

} // fim da classe SistemaService
