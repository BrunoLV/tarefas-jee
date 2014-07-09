package com.valhala.tarefa.web.beans;

import com.valhala.tarefa.ejb.TarefaService;
import com.valhala.tarefa.exceptions.ConsultaSemRetornoException;
import com.valhala.tarefa.model.TipoDemanda;

import org.primefaces.model.chart.PieChartModel;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import java.io.Serializable;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Managed Bean utilizado para as ações de tela relacionadas a gráficos.
 *
 * @author Bruno Luiz Viana
 * @version 1.0
 * @since 09/07/2014
 */
@Named("GraficoBean")
@RequestScoped
public class GraficoBean extends BaseJSFBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
    
    @EJB
    private TarefaService tarefaService;
    private PieChartModel graficoEquipes = new PieChartModel();
    
    private Date dataInicial;
    private Date dataFinal;
    private TipoDemanda tipoDemanda;

    @PostConstruct
    public void init() {
    	gerarGraficoEquipe(TipoDemanda.TODOS);
    } // fim do método init

    public void gerarGraficoEquipe(TipoDemanda tipo) {
        this.graficoEquipes = new PieChartModel();
        try {
            Map<String, BigInteger> mapa = this.tarefaService.buscarTotaisPorEquipeEPeriodoETipo(calcularDataInicial(), calcularDataFinal(), tipo);
            Set<String> chaves = mapa.keySet();
            for (String chave : chaves) {
                graficoEquipes.set(chave, mapa.get(chave).intValue());
            } // fim do bloco for
        } catch (ConsultaSemRetornoException | ParseException e) {
            this.graficoEquipes = new PieChartModel();
        	inserirMensagemDeErro(e.getMessage());
        } // fim do método
    }
    
    public void atualizarGraficoEquipe() {
    	gerarGraficoEquipe(this.tipoDemanda);
    }

    private Date calcularDataInicial() throws ParseException {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        return DATE_FORMAT.parse(String.format("%s/%s/%s", gregorianCalendar.getActualMinimum(Calendar.DAY_OF_MONTH),
                gregorianCalendar.get(Calendar.MONTH) + 1,
                gregorianCalendar.get(Calendar.YEAR)));
    }

    private Date calcularDataFinal() throws ParseException {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        return DATE_FORMAT.parse(String.format("%s/%s/%s", gregorianCalendar.getActualMaximum(Calendar.DAY_OF_MONTH),
                gregorianCalendar.get(Calendar.MONTH) + 1,
                gregorianCalendar.get(Calendar.YEAR)));
    }
    
    public TipoDemanda[] getTipo(){
    	return TipoDemanda.values();
    }

    public PieChartModel getGraficoEquipes() {
        return graficoEquipes;
    }
    
    public Date getDataFinal() {
		return dataFinal;
	}
    
    public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}
    
    public Date getDataInicial() {
		return dataInicial;
	}
    
    public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}
    
    public TipoDemanda getTipoDemanda() {
		return tipoDemanda;
	}
    
    public void setTipoDemanda(TipoDemanda tipoDemanda) {
		this.tipoDemanda = tipoDemanda;
	}

}
