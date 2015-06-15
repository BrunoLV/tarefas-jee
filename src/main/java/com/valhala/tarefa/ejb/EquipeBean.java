package com.valhala.tarefa.ejb;

import com.valhala.tarefa.dao.api.EquipeDao;
import com.valhala.tarefa.model.Equipe;
import com.valhala.tarefa.qualifiers.Auditavel;

import javax.ejb.*;
import javax.inject.Inject;
import java.util.List;

/**
 * EJB responsavel pela regra de negócio relacioanada a Equipe.
 *
 * @author Bruno Luiz Viana
 * @version 1.0
 * @since 04/06/2014
 */
@Auditavel
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class EquipeBean {

    @Inject
    private EquipeDao equipeDao;

    /**
     * Método utilizado para retornar a lista de todas as equipes registradas no sistema.
     *
     * @return
     * @throws ConsultaSemRetornoException
     */
    @Auditavel
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Equipe> buscarTodasEquipes() {
        return this.equipeDao.listarTudo();
    } // fim do método buscarTodasEquipes

} // fim da classe EquipeService