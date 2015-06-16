package com.valhala.tarefa.web.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import com.valhala.tarefa.ejb.ColaboradorBean;
import com.valhala.tarefa.model.Colaborador;
import com.valhala.tarefa.vo.ColaboradorVO;

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
    private ColaboradorBean colaboradorService;

    private ColaboradorVO colaborador;
    private List<ColaboradorVO> colaboradores;

    public ColaboradorMB() {
    }

    @PostConstruct
    public void init() {
        this.colaborador = new ColaboradorVO();
        this.inicializarColaboradores();
    } // fim do método init

    public ColaboradorVO getColaborador() {
        return colaborador;
    }

    public void setColaborador(ColaboradorVO colaborador) {
        this.colaborador = colaborador;
    }

    public List<ColaboradorVO> getColaboradores() {
        return colaboradores;
    }

    public void setColaboradores(List<ColaboradorVO> colaboradores) {
        this.colaboradores = colaboradores;
    }

    private void inicializarColaboradores() {
        this.colaboradores = new ArrayList<>();
        List<Colaborador> auxiliarList = this.colaboradorService.buscarTodosColaboradores();
        for (Colaborador colaborador : auxiliarList) {
			colaboradores.add(ColaboradorVO.fromModel(colaborador));
		}
    } // fim do método inicializarColaboradores

} // fim da classe ColaboradorBean