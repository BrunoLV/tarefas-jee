package com.valhala.tarefa.web.beans;

import com.valhala.tarefa.ejb.EquipeService;
import com.valhala.tarefa.ejb.TarefaService;
import com.valhala.tarefa.exceptions.ConsultaSemRetornoException;
import com.valhala.tarefa.model.Equipe;
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
    @EJB
    private EquipeService equipeService;
    
    private PieChartModel graficoEquipes = new PieChartModel();
    private PieChartModel graficoTarefas = new PieChartModel();
    
    private Date dataInicial;
    private Date dataFinal;
    
    private TipoDemanda tipoDemanda;
    private Long idEquipe;
    private List<Equipe> equipes;
    
    @PostConstruct
    public void init() {
    	try {
			this.equipes = this.equipeService.buscarTodasEquipes();
		} catch (ConsultaSemRetornoException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	this.idEquipe = Long.valueOf(0l);
    	this.tipoDemanda = TipoDemanda.TODOS;
    	try {
			this.dataInicial = calcularDataInicial();
		} catch (ParseException e) {
			this.dataInicial = new Date();
		}
    	try {
			this.dataFinal = calcularDataFinal();
		} catch (ParseException e) {
			this.dataFinal = new Date();
		}
    	gerarGraficoEquipe();
    	gerarGraficoIncidentes();
    } // fim do método init

    public void gerarGraficoEquipe() {
        this.graficoEquipes = new PieChartModel();
        try {
            Map<String, BigInteger> mapa = this.tarefaService.buscarTotaisPorEquipeEPeriodoETipo(this.dataInicial, this.dataFinal, this.tipoDemanda);
            Set<String> chaves = mapa.keySet();
            for (String chave : chaves) {
                graficoEquipes.set(chave, mapa.get(chave).intValue());
            } // fim do bloco for
        } catch (ConsultaSemRetornoException e) {
        	inserirMensagemDeErro(e.getMessage());
        } // fim do método
    }
    
    public void gerarGraficoIncidentes() {
    	this.graficoTarefas = new PieChartModel();
    	try {
			Map<String, BigInteger> mapa = this.tarefaService.buscarTotaisTodosSistemaPorTipoEEquipe(this.dataInicial, this.dataFinal, this.idEquipe, this.tipoDemanda);
			Set<String> chaves = mapa.keySet();
			for (String chave : chaves) {
				graficoTarefas.set(chave, mapa.get(chave).intValue());
			}
		} catch (ConsultaSemRetornoException e) {
			inserirMensagemDeErro(e.getMessage());
		}
    }
    
    public void atualizarGraficoEquipe() {
    	gerarGraficoEquipe();
    }
    
    public void atualizarGraficoTarefa() {
    	gerarGraficoIncidentes();
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
    
    public PieChartModel getGraficoTarefas() {
		return graficoTarefas;
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
    
    public Long getIdEquipe() {
		return idEquipe;
	}
    
    public void setIdEquipe(Long idEquipe) {
		this.idEquipe = idEquipe;
	}
    
    public List<Equipe> getEquipes() {
		return equipes;
	}
    
    public void setEquipes(List<Equipe> equipes) {
		this.equipes = equipes;
	}

}
