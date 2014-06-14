package com.valhala.tarefa.dao.impl;

import com.valhala.tarefa.dao.api.SistemaDao;
import com.valhala.tarefa.exceptions.ConsultaSemRetornoException;
import com.valhala.tarefa.model.Sistema;

import javax.persistence.TypedQuery;
import java.util.List;

public class SistemaDaoImpl extends BaseDao<Sistema> implements SistemaDao {

    public static final Class<Sistema> CLASSE_PERSISTENTE = Sistema.class;

    public SistemaDaoImpl() {
        this.classePersistente = SistemaDaoImpl.CLASSE_PERSISTENTE;
    }

    @Override
    public List<Sistema> listarTudo() throws ConsultaSemRetornoException {
        TypedQuery<Sistema> query = this.entityManager.createNamedQuery(Sistema.NAMEDQUERY_BUSCAR_TODOS, SistemaDaoImpl.CLASSE_PERSISTENTE);
        List<Sistema> sistemas = query.getResultList();
        if (sistemas.isEmpty()) {
            throw new ConsultaSemRetornoException("Consulta não trouxe resultados.");
        } // fim do bloco if
        return sistemas;
    }

}
