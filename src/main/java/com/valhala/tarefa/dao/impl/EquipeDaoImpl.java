package com.valhala.tarefa.dao.impl;

import java.util.List;

import javax.persistence.TypedQuery;

import com.valhala.tarefa.dao.api.EquipeDao;
import com.valhala.tarefa.model.Equipe;

/**
 * Classe que implementa EquipeDao.
 *
 * @author Bruno Luiz Viana
 * @version 1.0
 * @since 23/06/2014
 */
public class EquipeDaoImpl extends BaseDao<Equipe> implements EquipeDao {

    public static final Class<Equipe> CLASSE_PERSISTENTE = Equipe.class;

    public EquipeDaoImpl() {
        this.classePersistente = EquipeDaoImpl.CLASSE_PERSISTENTE;
    } // fim do método construtor

    @Override
    public List<Equipe> listarTudo() {
        TypedQuery<Equipe> query = this.entityManager.createNamedQuery(Equipe.NAMEDQUERY_BUSCAR_TODOS, EquipeDaoImpl.CLASSE_PERSISTENTE);
        List<Equipe> equipes = query.getResultList();
        return equipes;
    } // fim do método listarTudo

    @Override
    public Equipe buscarPorNome(String nome) {
        TypedQuery<Equipe> query = this.entityManager.createNamedQuery(Equipe.NAMEDQUERY_BUSCAR_POR_NOME, EquipeDaoImpl.CLASSE_PERSISTENTE);
        query.setParameter("nome", nome);
        Equipe equipe = query.getSingleResult();
        return equipe;
    } // fim do método buscarPorNome

} // fim da classe EquipeDaoImpl
