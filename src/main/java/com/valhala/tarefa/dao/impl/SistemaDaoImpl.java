package com.valhala.tarefa.dao.impl;

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
public class SistemaDaoImpl extends BaseDao<Sistema> implements SistemaDao {

    public static final Class<Sistema> CLASSE_PERSISTENTE = Sistema.class;

    public SistemaDaoImpl() {
        this.classePersistente = SistemaDaoImpl.CLASSE_PERSISTENTE;
    } // fim do método construtor

    @Override
    public List<Sistema> listarTudo() {
        TypedQuery<Sistema> query = this.entityManager.createNamedQuery(Sistema.NAMEDQUERY_BUSCAR_TODOS, SistemaDaoImpl.CLASSE_PERSISTENTE);
        List<Sistema> sistemas = query.getResultList();
        return sistemas;
    } // fim do método listarTudo

} // fim da classe SistemaDaoImpl