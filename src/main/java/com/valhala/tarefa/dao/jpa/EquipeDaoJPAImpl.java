package com.valhala.tarefa.dao.jpa;

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
class EquipeDaoJPAImpl extends BaseJPADao<Equipe> implements EquipeDao {

    public EquipeDaoJPAImpl() {
        this.classePersistente = Equipe.class;
    } // fim do método construtor

    @Override
    public List<Equipe> listarTudo() {
        TypedQuery<Equipe> query = construirTypedQuery(Equipe.NAMEDQUERY_BUSCAR_TODOS);
        List<Equipe> equipes = query.getResultList();
        return equipes;
    } // fim do método listarTudo

    @Override
    public Equipe buscarPorNome(String nome) {
        TypedQuery<Equipe> query = construirTypedQuery(Equipe.NAMEDQUERY_BUSCAR_POR_NOME, nome);
        Equipe equipe = query.getSingleResult();
        return equipe;
    } // fim do método buscarPorNome

} // fim da classe EquipeDaoImpl
