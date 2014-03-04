package com.valhala.tarefa.revision.listeners;

import java.util.Date;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.envers.RevisionListener;

import com.valhala.tarefa.model.Revisao;

/**
 * Listener de auditoria.
 * Utilizado para preparar a entidade de revisão para a auditoria.
 * @author Bruno Luiz Viana
 * @version 1.0
 * @since 02/03/2014
 *
 */
public class RevisaoListener implements RevisionListener {
	
	public RevisaoListener() {
	} // fim do método construtor
	
	@Override
	public void newRevision(Object objetoRevisao) {
		Revisao revisao = (Revisao) objetoRevisao;
		revisao.setTimestamp(new Date());
		revisao.setAutor(((HttpServletRequest)FacesContext.
				getCurrentInstance().
				getExternalContext().
				getRequest()).
				getUserPrincipal().
				getName());
	} // fim do método newRevision

} // fim da classe RevisaoListener