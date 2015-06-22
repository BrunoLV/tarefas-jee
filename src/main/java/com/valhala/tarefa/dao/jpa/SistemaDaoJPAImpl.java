package com.valhala.tarefa.dao.jpa;

import java.util.List;

import javax.persistence.TypedQuery;

import com.valhala.tarefa.dao.api.SistemaDao;
import com.valhala.tarefa.model.Sistema;

/**
 * Classe que implementa SistemaDao.
 *
 * @author Bruno Luiz Viana
 * @version 1.0
 * @since 23/06/2014
 */
class SistemaDaoJPAImpl extends BaseJPADao<Sistema> implements SistemaDao {

    public SistemaDaoJPAImpl() {
        this.classePersistente = Sistema.class;
    } // fim do método construtor

    @Override
    public List<Sistema> listarTudo() {
        TypedQuery<Sistema> query = construirTypedQuery(Sistema.NAMEDQUERY_BUSCAR_TODOS);
        List<Sistema> sistemas = query.getResultList();
        return sistemas;
    } // fim do método listarTudo

} // fim da classe SistemaDaoImpl
