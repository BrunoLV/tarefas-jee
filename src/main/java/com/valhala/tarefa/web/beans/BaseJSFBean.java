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
 *
 * @author Bruno Luiz Viana
 * @version 1.0
 * @since 23/02/2014
 */
public class BaseJSFBean {

    /**
     * Método utilizado para inserir uma mensagem de sucesso no contexto.
     *
     * @param mensagem
     */
    protected void inserirMensagemDeSucesso(String mensagem) {
        inserirMensagem(FacesMessage.SEVERITY_INFO, mensagem);
    } // fim do método inserirMensagemDeSucesso

    /**
     * Método utilizado para inserir uma mensagem de erro no contexto.
     *
     * @param mensagem
     */
    protected void inserirMensagemDeErro(String mensagem) {
        inserirMensagem(FacesMessage.SEVERITY_ERROR, mensagem);
    } // fim do método inserirMensagemDeErro

    /**
     * Método utilizado efetivamente para inserir mensagem no contexto.
     *
     * @param severidade
     * @param mensagem
     */
    private void inserirMensagem(Severity severidade, String mensagem) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severidade, mensagem, null));
    } // fim do método inserirMensagems

    /**
     * Método utilizado para recuperar o FacesContext da requisição.
     *
     * @return
     */
    protected FacesContext getFacesContext() {
        return FacesContext.getCurrentInstance();
    } // fim do método getFacesContext

    /**
     * Método utilizado para recuperar a request a ser executada.
     *
     * @return
     */
    protected HttpServletRequest getRequest() {
        return (HttpServletRequest) getFacesContext().getExternalContext().getRequest();
    } // fim do método getRequest

    /**
     * Método utilizado para recuperar a session.
     *
     * @return
     */
    protected HttpSession getSession() {
        return getRequest().getSession();
    } // fim do método getSession

    /**
     * Método utilizado para obter o escopo Flash da aplicação.
     *
     * @return
     */
    protected Flash getFlashScope() {
        return getFacesContext().getExternalContext().getFlash();
    } // fim do método getFlashScope

    /**
     * Método uitilizado para inserir um objeto no FlashScope
     *
     * @param chave
     * @param valor
     */
    protected void inserirObjetoNoFlashScope(String chave, Object valor) {
        getFlashScope().put(chave, valor);
        getFlashScope().setKeepMessages(true);
    } // fim do método inserirObjetoNoFlashScope

    /**
     * Método utilizado para verificar a existencia de determinado objeto no FlashScope
     *
     * @param chave
     * @return
     */
    protected boolean verificarExistenciaObjetoFlashScope(Object chave) {
        return getFlashScope().containsKey(chave);
    } // fim do método verificarExistenciaObjetoFlashScope

    /**
     * Método utiliza para recuperar um objeto do FlashScope
     *
     * @param chave
     * @return
     */
    protected Object obterObjetoDoFlashScope(Object chave) {
        return getFlashScope().get(chave);
    } // fim do método obterObjetoDoFlashScope

    /**
     * Método uitilizado para inserir um objeto no SessionScope
     *
     * @param chave
     * @param valor
     */
    protected void inserirObjetoNaSession(String chave, Object valor) {
        getSession().setAttribute(chave, valor);
    } // fim do método inserirObjetoNaSession

    /**
     * Método uitilizado para remover um objeto no SessionScope
     *
     * @param chave
     * @param valor
     */
    protected void removerObjetoDaSession(String chave) {
        getSession().removeAttribute(chave);
    } // fim do método removerObjetoDaSession

    /**
     * Método utiliza para recuperar um objeto do SessionScope
     *
     * @param chave
     * @return
     */
    protected Object obterObjectDaSession(String chave) {
        return getSession().getAttribute(chave);
    } // fim do método obterObjectDaSession

} // fim da classe BaseJSFBean