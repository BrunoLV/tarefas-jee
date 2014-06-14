package com.valhala.tarefa.dao.impl;

import com.valhala.tarefa.dao.api.ColaboradorDao;
import com.valhala.tarefa.exceptions.ConsultaSemRetornoException;
import com.valhala.tarefa.model.Colaborador;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Classe que implementa ColaboradorDao
 *
 * @author Bruno Luiz Viana
 * @version 1.0
 * @since 23/02/2014
 */
public class ColaboradorDaoImpl extends BaseDao<Colaborador> implements ColaboradorDao {

    public static final Class<Colaborador> CLASSE_PERSISTENTE = Colaborador.class;

    public ColaboradorDaoImpl() {
        this.classePersistente = ColaboradorDaoImpl.CLASSE_PERSISTENTE;
    } // fim do método construtor

    @Override
    public List<Colaborador> listarTudo() throws ConsultaSemRetornoException {
        TypedQuery<Colaborador> query = this.entityManager.createNamedQuery(Colaborador.NAMEDQUERY_BUSCAR_TODOS, ColaboradorDaoImpl.CLASSE_PERSISTENTE);
        List<Colaborador> colaboradores = query.getResultList();
        if (colaboradores.isEmpty()) {
            throw new ConsultaSemRetornoException("Consulta não trouxe resultados.");
        } // fim do bloco if
        return colaboradores;
    } // fim do método listarTudo

    @Override
    public Colaborador buscarPorMatricula(String matricula) throws ConsultaSemRetornoException {
        TypedQuery<Colaborador> query = this.entityManager.createNamedQuery(Colaborador.NAMEDQUERY_BUSCAR_POR_MATRICULA, ColaboradorDaoImpl.CLASSE_PERSISTENTE);
        query.setParameter("matricula", matricula);
        Colaborador colaborador;
        try {
            colaborador = query.getSingleResult();
        } catch (NoResultException e) {
            throw new ConsultaSemRetornoException(e.getMessage(), e);
        } // fim do bloco try/catch
        return colaborador;
    } // fim do método buscarPorMatricula

    @Override
    public Colaborador buscarPorNome(String nome) throws ConsultaSemRetornoException {
        TypedQuery<Colaborador> query = this.entityManager.createNamedQuery(Colaborador.NAMEDQUERY_BUSCAR_POR_NOME, ColaboradorDaoImpl.CLASSE_PERSISTENTE);
        query.setParameter("nome", nome);
        Colaborador colaborador;
        try {
            colaborador = query.getSingleResult();
        } catch (NoResultException e) {
            throw new ConsultaSemRetornoException(e.getMessage(), e);
        } // fim do bloco try/catch
        return colaborador;
    } // fim do método buscarPorNome

} // fim da classe ColaboradorDaoImpl