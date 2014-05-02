package com.valhala.tarefa.dao.impl;

import java.util.List;

import javax.persistence.TypedQuery;

import com.valhala.tarefa.dao.api.ClienteDao;
import com.valhala.tarefa.exceptions.ConsultaSemRetornoException;
import com.valhala.tarefa.model.Cliente;

public class ClienteDaoImpl extends BaseDao<Cliente> implements ClienteDao {
	
	public static final Class<Cliente> CLASSE_PERSISTENTE = Cliente.class;

	public ClienteDaoImpl() {
		this.classePersistente = ClienteDaoImpl.CLASSE_PERSISTENTE;
	}
	
	@Override
	public List<Cliente> listarTudo() throws ConsultaSemRetornoException {
		TypedQuery<Cliente> query = this.entityManager.createNamedQuery(Cliente.NAMEDQUERY_BUSCAR_TODOS, ClienteDaoImpl.CLASSE_PERSISTENTE);
		List<Cliente> clientes = query.getResultList();
		if (clientes.isEmpty()) {
			throw new ConsultaSemRetornoException("Consulta não trouxe resultados.");
		} // fim do bloco if
		return clientes;
	}

}
