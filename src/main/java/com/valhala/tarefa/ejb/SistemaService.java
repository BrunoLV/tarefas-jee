package com.valhala.tarefa.ejb;

import com.valhala.tarefa.dao.api.SistemaDao;
import com.valhala.tarefa.exceptions.ConsultaSemRetornoException;
import com.valhala.tarefa.model.Sistema;
import com.valhala.tarefa.qualifiers.Auditavel;

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
public class SistemaService {

    @Inject
    private SistemaDao sistemaDao;

    /**
     * Método utilizado para retornar a lista de todos sistemas cadastrados na aplicação.
     *
     * @return
     * @throws ConsultaSemRetornoException
     */
    @Auditavel
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Sistema> buscarTodosSistemas() throws ConsultaSemRetornoException {
        return this.sistemaDao.listarTudo();
    } // fim do buscarTodosSistemas

} // fim da classe SistemaService