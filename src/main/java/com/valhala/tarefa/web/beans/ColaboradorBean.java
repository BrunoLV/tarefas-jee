package com.valhala.tarefa.web.beans;

import com.valhala.tarefa.ejb.ColaboradorService;
import com.valhala.tarefa.exceptions.ConsultaSemRetornoException;
import com.valhala.tarefa.model.Colaborador;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Managed Bean utilizado para as ações de tela relacionadas a colaboradores.
 *
 * @author Bruno Luiz Viana
 * @version 1.0
 * @since 23/02/2014
 */
@Named("ColaboradorBean")
@RequestScoped
public class ColaboradorBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private ColaboradorService colaboradorService;

    private Colaborador colaborador;
    private List<Colaborador> colaboradores;

    public ColaboradorBean() {
    }

    @PostConstruct
    public void init() {
        this.colaborador = new Colaborador();
        this.inicializarColaboradores();
    } // fim do método init

    public Colaborador getColaborador() {
        return colaborador;
    }

    public void setColaborador(Colaborador colaborador) {
        this.colaborador = colaborador;
    }

    public List<Colaborador> getColaboradores() {
        return colaboradores;
    }

    public void setColaboradores(List<Colaborador> colaboradores) {
        this.colaboradores = colaboradores;
    }

    private void inicializarColaboradores() {
        try {
            this.colaboradores = this.colaboradorService.buscarTodosColaboradores();
        } catch (ConsultaSemRetornoException e) {
            this.colaboradores = new ArrayList<>();
        } // fim do bloco try/catch
    } // fim do método inicializarColaboradores

} // fim da classe ColaboradorBean