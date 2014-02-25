package com.valhala.tarefa.web.beans;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Classe base dos managed beas da aplicação.
 * Essa classe possui os métodos comuns aos managed beans para manopulação de mensagem no contexto
 * e sessão.
 * @author Bruno Luiz Viana
 * @version 1.0
 * @since 23/02/2014
 *
 */
public class BaseJSFBean {
	
	protected void inserirMensagemDeSucesso(String mensagem) {
		inserirMensagem(FacesMessage.SEVERITY_INFO, mensagem);
	}
	
	protected void inserirMensagemDeErro(String mensagem) {
		inserirMensagem(FacesMessage.SEVERITY_ERROR, mensagem);
	}
	
	private void inserirMensagem(Severity severidade, String mensagem) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severidade, mensagem, null));
	}
	
	protected FacesContext getFacesContext() {
		return FacesContext.getCurrentInstance();
	}
	
	protected HttpServletRequest getRequest() {
		return (HttpServletRequest) getFacesContext().getExternalContext().getRequest();
	}
	
	protected HttpSession getSession() {
		return getRequest().getSession(false);
	}
	
	protected Flash getFlashScope() {
		return getFacesContext().getExternalContext().getFlash();
	}
	
	protected void inserirObjetoNoFlashScope(String chave, Object valor) {
		getFlashScope().put(chave, valor);
		getFlashScope().setKeepMessages(true);
	}
	
	protected boolean verificarExistenciaObjetoFlashScope(Object chave) {
		return getFlashScope().containsKey(chave);
	}
	
	protected Object obterObjetoDoFlashScope(Object chave) {
		return getFlashScope().get(chave);
	}
	
	protected void inserirObjetoNaSession(String chave, Object valor) {
		getSession().setAttribute(chave, valor);
	}
	
	protected void removerObjetoDaSession(String chave) {
		getSession().removeAttribute(chave);
	}
	
	protected Object obterObjectDaSession(String chave) {
		return getSession().getAttribute(chave);
	}
	
} // fim da classe BaseJSFBean