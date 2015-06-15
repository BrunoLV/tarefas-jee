package com.valhala.tarefa.web.beans;

import java.io.Serializable;
import java.security.Principal;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.servlet.ServletException;

import com.valhala.tarefa.ejb.ColaboradorBean;
import com.valhala.tarefa.exceptions.ServiceException;
import com.valhala.tarefa.model.Atribuicao;
import com.valhala.tarefa.vo.ColaboradorVO;

/**
 * Managed Bean utilizado para as ações de tela relacionadas a login.
 *
 * @author Bruno Luiz Viana
 * @version 1.0
 * @since 23/02/2014
 */
@Named("LoginMB")
@RequestScoped
public class LoginMB extends BaseMB implements Serializable {

    public static final String USUARIO_LOGADO = "USUARIO_LOGADO";
    private static final long serialVersionUID = 1L;
    private static final String OUTCOME_SUCESSO = "/pages/public/visualizacao-tarefas.xhtml?faces-redirect=true";
    private static final String OUTCOME_FALHA = "/pages/public/error.xhtml?faces-redirect=true";
    private static final String OUTCOME_LOGOUT = "/pages/public/login.xhtml?faces-redirect=true";
    private String matricula = "";
    private String senha = "";
    private String confirmeSenha = "";

    @EJB
    private ColaboradorBean colaboradorService;

    public LoginMB() {
        this.matricula = "";
        this.senha = "";
    } // fim do método construtor

    /**
     * Método utilizado para execução da ação de login dentro do sistema.
     *
     * @return
     */
    public String login() {
        String outcome;
        try {
            getRequest().login(this.matricula, this.senha);
            Principal principal = getRequest().getUserPrincipal();
            getSession().setAttribute(LoginMB.USUARIO_LOGADO, ColaboradorVO.fromModel(this.colaboradorService.buscarPorMatricula(principal.getName())));
            outcome = LoginMB.OUTCOME_SUCESSO;
        } catch (ServletException e) {
            System.out.println(e.getMessage());
            outcome = LoginMB.OUTCOME_FALHA;
        } // fim do bloco try/catch
        return outcome;
    } // fim do método login

    /**
     * Método utilizado para ação de redefinição de senha.
     */
    public void redefinirSenha() {
        if (this.senha.equals(this.confirmeSenha)) {
            try {
                ColaboradorVO colaborador = (ColaboradorVO) getSession().getAttribute(LoginMB.USUARIO_LOGADO);
                colaborador.setSenha(this.senha);
                this.colaboradorService.editarColaborador(colaborador.asModel());
                getSession().setAttribute(LoginMB.USUARIO_LOGADO, colaborador);
                inserirMensagemDeSucesso("Senha redefinida com sucesso.");
            } catch (ServiceException e) {
                inserirMensagemDeErro(String.format("Ocorreu um erro durante a ação: %s", e.getMessage()));
            } // fim do bloco try/catch
        } else {
            inserirMensagemDeErro("As senhas não conferem. Digite novamente.");
        } // fim do bloco if/else
    } // fim do método redefinirSenha

    public boolean isLiderOrManter() {
        return isLider() || isManter() ? true : false;
    }// fim do método isLiderOrManter

    public boolean isLider() {
        return getRequest().isUserInRole(Atribuicao.LIDER.toString()) ? true : false;
    } // fim do método isLider

    public boolean isManter() {
        return getRequest().isUserInRole(Atribuicao.MANTER.toString()) ? true : false;
    } // fim do método isManter

    /**
     * Método utilizado para execução da ação de logout do sistema.
     *
     * @return
     */
    public String logout() {
        if (getSession() != null) {
            getSession().invalidate();
        } // fim do bloco if
        return LoginMB.OUTCOME_LOGOUT;
    } // fim do método logout

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

    public String getConfirmeSenha() {
        return confirmeSenha;
    }

    public void setConfirmeSenha(String confirmeSenha) {
        this.confirmeSenha = confirmeSenha;
    }

} // fim da classe TarefaBean