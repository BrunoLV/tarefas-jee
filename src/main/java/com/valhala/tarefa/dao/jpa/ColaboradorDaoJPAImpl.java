package com.valhala.tarefa.dao.jpa;

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
class ColaboradorDaoJPAImpl extends BaseJPADao<Colaborador> implements ColaboradorDao {

    public ColaboradorDaoJPAImpl() {
        this.classePersistente = Colaborador.class;
    } // fim do método construtor

    @Override
    public List<Colaborador> listarTudo() {
        TypedQuery<Colaborador> query = construirTypedQuery(Colaborador.NAMEDQUERY_BUSCAR_TODOS);
        List<Colaborador> colaboradores = query.getResultList();
        return colaboradores;
    } // fim do método listarTudo

    @Override
    public Colaborador buscarPorMatricula(String matricula) {
        TypedQuery<Colaborador> query = construirTypedQuery(Colaborador.NAMEDQUERY_BUSCAR_POR_MATRICULA, matricula); 
        Colaborador colaborador = query.getSingleResult();
        return colaborador;
    } // fim do método buscarPorMatricula

    @Override
    public Colaborador buscarPorNome(String nome) {
        TypedQuery<Colaborador> query = construirTypedQuery(Colaborador.NAMEDQUERY_BUSCAR_POR_NOME, nome);
        Colaborador colaborador = query.getSingleResult();
        return colaborador;
    } // fim do método buscarPorNome

} // fim da classe ColaboradorDaoImpl
