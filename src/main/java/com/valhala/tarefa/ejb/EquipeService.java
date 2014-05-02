package com.valhala.tarefa.ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;

import com.valhala.tarefa.dao.api.EquipeDao;
import com.valhala.tarefa.exceptions.ConsultaSemRetornoException;
import com.valhala.tarefa.model.Equipe;
import com.valhala.tarefa.qualifiers.Auditavel;

@Auditavel
@Stateless @TransactionManagement(TransactionManagementType.CONTAINER)
public class EquipeService {
	
	@Inject
	private EquipeDao equipeDao;
	
	@Auditavel
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Equipe> buscarTodasEquipes() throws ConsultaSemRetornoException {
		return this.equipeDao.listarTudo();
	}

}
