package com.valhala.tarefa.web.managedbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import com.valhala.tarefa.ejb.ColaboradorEJB;
import com.valhala.tarefa.model.Colaborador;
import com.valhala.tarefa.dtos.ColaboradorDTO;

/**
 * Managed Bean utilizado para as ações de tela relacionadas a colaboradores.
 *
 * @author Bruno Luiz Viana
 * @version 1.0
 * @since 23/02/2014
 */
@Named("ColaboradorMB")
@RequestScoped
public class ColaboradorMB implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private ColaboradorEJB colaboradorService;

    private ColaboradorDTO colaborador;
    private List<ColaboradorDTO> colaboradores;

    public ColaboradorMB() {
    }

    @PostConstruct
    public void init() {
        this.colaborador = new ColaboradorDTO();
        this.inicializarColaboradores();
    } // fim do método init

    public ColaboradorDTO getColaborador() {
        return colaborador;
    }

    public void setColaborador(ColaboradorDTO colaborador) {
        this.colaborador = colaborador;
    }

    public List<ColaboradorDTO> getColaboradores() {
        return colaboradores;
    }

    public void setColaboradores(List<ColaboradorDTO> colaboradores) {
        this.colaboradores = colaboradores;
    }

    private void inicializarColaboradores() {
        this.colaboradores = new ArrayList<>();
        List<Colaborador> auxiliarList = this.colaboradorService.buscarTodosColaboradores();
        for (Colaborador colaborador : auxiliarList) {
            colaboradores.add(ColaboradorDTO.fromModel(colaborador));
        }
    } // fim do método inicializarColaboradores

} // fim da classe ColaboradorBean
