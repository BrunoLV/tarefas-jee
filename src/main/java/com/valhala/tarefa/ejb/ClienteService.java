package com.valhala.tarefa.ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;

import com.valhala.tarefa.dao.api.ClienteDao;
import com.valhala.tarefa.exceptions.ConsultaSemRetornoException;
import com.valhala.tarefa.model.Cliente;
import com.valhala.tarefa.qualifiers.Auditavel;

@Auditavel
@Stateless @TransactionManagement(TransactionManagementType.CONTAINER)
public class ClienteService {
	
	@Inject
	private ClienteDao clienteDao;
	
	@Auditavel
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Cliente> buscarTodosClientes() throws ConsultaSemRetornoException {
		return this.clienteDao.listarTudo();
	}

}
