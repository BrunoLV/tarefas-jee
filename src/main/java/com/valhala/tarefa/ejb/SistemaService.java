package com.valhala.tarefa.ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;

import com.valhala.tarefa.dao.api.SistemaDao;
import com.valhala.tarefa.exceptions.ConsultaSemRetornoException;
import com.valhala.tarefa.model.Sistema;
import com.valhala.tarefa.qualifiers.Auditavel;

@Auditavel
@Stateless @TransactionManagement(TransactionManagementType.CONTAINER)
public class SistemaService {
	
	@Inject
	private SistemaDao sistemaDao;
	
	@Auditavel
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Sistema> buscarTodosSistemas() throws ConsultaSemRetornoException {
		return this.sistemaDao.listarTudo();
	}

}
