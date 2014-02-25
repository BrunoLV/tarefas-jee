package com.valhala.tarefa.web.beans;

import java.io.Serializable;
import java.security.Principal;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.servlet.ServletException;

import com.valhala.tarefa.ejb.ColaboradorService;
import com.valhala.tarefa.exceptions.ConsultaSemRetornoException;
import com.valhala.tarefa.model.Atribuicao;

/**
 * Managed Bean utilizado para as ações de tela relacionadas a login.
 * @author Bruno Luiz Viana
 * @version 1.0
 * @since 23/02/2014
 *
 */
@Named("LoginBean")
@RequestScoped
public class LoginBean extends BaseJSFBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String matricula;
	private String senha;
	
	private static final String USUARIO_LOGADO = "USUARIO_LOGADO";
	
	private static final String OUTCOME_SUCESSO = "/pages/public/visualizacao-tarefas.xhtml?faces-redirect=true";
	private static final String OUTCOME_FALHA = "/pages/public/error.xhtml?faces-redirect=true";
	private static final String OUTCOME_LOGOUT = "/pages/public/login.xhtml?faces-redirect=true";
	
	@EJB
	private ColaboradorService colaboradorService;
	
	public LoginBean() {
		// TODO Auto-generated constructor stub
	}
	
	public String login() {
		String outcome;
		try {
			getRequest().login(this.matricula, this.senha);
			Principal principal = getRequest().getUserPrincipal();
			getSession().setAttribute(LoginBean.USUARIO_LOGADO, this.colaboradorService.buscarPorMatricula(principal.getName()));
			outcome = LoginBean.OUTCOME_SUCESSO;
		} catch (ServletException | ConsultaSemRetornoException e) {
			outcome = LoginBean.OUTCOME_FALHA;
		}
		return outcome;
	}
	
	public boolean isLiderOrManter() {
		return isLider() || isManter() ? true : false;
	}
	
	public boolean isLider() {
		return getRequest().isUserInRole(Atribuicao.LIDER.toString()) ? true : false;
	}
	
	public boolean isManter() {
		return getRequest().isUserInRole(Atribuicao.MANTER.toString()) ? true : false;
	}
	
	public String logout() {
		if (getSession() != null) {
			getSession().invalidate();
		}
		return LoginBean.OUTCOME_LOGOUT;
	}
	
	public String getMatricula() {
		return matricula;
	}
	
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
	
	public String getSenha() {
		return senha;
	}
	
	public void setSenha(String senha) {
		this.senha = senha;
	}

} // fim da classe TarefaBean