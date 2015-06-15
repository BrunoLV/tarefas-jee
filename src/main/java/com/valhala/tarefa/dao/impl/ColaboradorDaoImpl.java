package com.valhala.tarefa.dao.impl;

import java.util.List;

import javax.persistence.TypedQuery;

import com.valhala.tarefa.dao.api.ColaboradorDao;
import com.valhala.tarefa.model.Colaborador;

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
    public List<Colaborador> listarTudo() {
        TypedQuery<Colaborador> query = this.entityManager.createNamedQuery(Colaborador.NAMEDQUERY_BUSCAR_TODOS, ColaboradorDaoImpl.CLASSE_PERSISTENTE);
        List<Colaborador> colaboradores = query.getResultList();
        return colaboradores;
    } // fim do método listarTudo

    @Override
    public Colaborador buscarPorMatricula(String matricula) {
        TypedQuery<Colaborador> query = this.entityManager.createNamedQuery(Colaborador.NAMEDQUERY_BUSCAR_POR_MATRICULA, ColaboradorDaoImpl.CLASSE_PERSISTENTE);
        query.setParameter("matricula", matricula);
        Colaborador colaborador = query.getSingleResult();
        return colaborador;
    } // fim do método buscarPorMatricula

    @Override
    public Colaborador buscarPorNome(String nome) {
        TypedQuery<Colaborador> query = this.entityManager.createNamedQuery(Colaborador.NAMEDQUERY_BUSCAR_POR_NOME, ColaboradorDaoImpl.CLASSE_PERSISTENTE);
        query.setParameter("nome", nome);
        Colaborador colaborador = query.getSingleResult();
        return colaborador;
    } // fim do método buscarPorNome

} // fim da classe ColaboradorDaoImpl