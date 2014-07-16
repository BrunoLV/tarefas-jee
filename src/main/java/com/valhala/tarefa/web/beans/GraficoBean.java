package com.valhala.tarefa.web.beans;

import java.io.Serializable;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import org.primefaces.model.chart.PieChartModel;

import com.valhala.tarefa.ejb.ClienteService;
import com.valhala.tarefa.ejb.EquipeService;
import com.valhala.tarefa.ejb.TarefaService;
import com.valhala.tarefa.exceptions.ConsultaSemRetornoException;
import com.valhala.tarefa.model.Equipe;
import com.valhala.tarefa.model.TipoDemanda;

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
    @EJB
    private ClienteService clienteService;
    
    private PieChartModel graficoDemandasEquipes;
    private PieChartModel graficoDemandasSistemas;
    private PieChartModel graficoDemandasCliente;
    
    private Date dataInicial;
    private Date dataFinal;
    
    private TipoDemanda tipoDemanda;
    
    private Long idEquipe;
    
    private List<Equipe> equipes;
    
    @PostConstruct
    public void init() {
    	try {
			this.equipes = this.equipeService.buscarTodasEquipes();
			this.idEquipe = Long.valueOf(0l);
	    	this.tipoDemanda = TipoDemanda.TODOS;
	    	try {
				this.dataInicial = calcularDataInicial();
				this.dataFinal = calcularDataFinal();
			} catch (ParseException e) {
				this.dataInicial = new Date();
				this.dataFinal = new Date();
			} // fim do bloco try/catch
	    	this.gerarGraficoDemandasEquipe();
	    	this.gerarGraficoDemandasSistemas();
	    	this.gerarGraficoDemandasClientes();
		} catch (ConsultaSemRetornoException e) {
			inserirMensagemDeErro(e.getMessage());
		}
    } // fim do método init

    public void gerarGraficoDemandasEquipe() {
        this.graficoDemandasEquipes = new PieChartModel();
        try {
            Map<String, BigInteger> mapa = this.tarefaService.buscarTotaisPorEquipeEPeriodoETipo(this.dataInicial, this.dataFinal, this.tipoDemanda);
            Set<String> chaves = mapa.keySet();
            Map<String, Number> valor = new LinkedHashMap<>();
            for (String chave : chaves) {
            	valor.put(chave, mapa.get(chave));
            } // fim do bloco for
            graficoDemandasEquipes.setData(valor);
        } catch (ConsultaSemRetornoException e) {
        	graficoDemandasEquipes = new PieChartModel();
        	inserirMensagemDeErro(e.getMessage());
        } // fim do método
    }
    
    public void gerarGraficoDemandasSistemas() {
    	this.graficoDemandasSistemas = new PieChartModel();
    	try {
			Map<String, BigInteger> mapa = this.tarefaService.buscarTotaisTodosSistemaPorTipoEEquipe(this.dataInicial, this.dataFinal, this.idEquipe, this.tipoDemanda);
			Set<String> chaves = mapa.keySet();
			Map<String, Number> valor = new LinkedHashMap<>();
			for (String chave : chaves) {
				valor.put(chave, mapa.get(chave));
			}
			graficoDemandasSistemas.setData(valor);
		} catch (ConsultaSemRetornoException e) {
			graficoDemandasSistemas = new PieChartModel();
			inserirMensagemDeErro(e.getMessage());
		}
    }
    
    public void gerarGraficoDemandasClientes() {
    	this.graficoDemandasCliente = new PieChartModel();
    	try {
			Map<String, BigInteger> mapa = this.tarefaService.buscarTotaisTodosClientesPorTipoEEquipe(this.dataInicial, this.dataFinal, this.idEquipe, this.tipoDemanda);
			Set<String> chaves = mapa.keySet();
			Map<String, Number> valor = new LinkedHashMap<>();
			for (String chave : chaves) {
				valor.put(chave, mapa.get(chave));
			}
			graficoDemandasCliente.setData(valor);
		} catch (ConsultaSemRetornoException e) {
			graficoDemandasCliente = new PieChartModel();
			inserirMensagemDeErro(e.getMessage());
		}
    }
    
    public void atualizarGraficoEquipe() {
    	gerarGraficoDemandasEquipe();
    }
    
    public void atualizarGraficoTarefa() {
    	gerarGraficoDemandasSistemas();
    }
    
    public void atualizarGraficoCliente() {
    	gerarGraficoDemandasClientes();
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
    
    public List<Equipe> getEquipes() {
		return equipes;
	}
    
    public void setEquipes(List<Equipe> equipes) {
		this.equipes = equipes;
	}

	public Date getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}

	public Date getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
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

	public PieChartModel getGraficoDemandasEquipes() {
		return graficoDemandasEquipes;
	}

	public PieChartModel getGraficoDemandasSistemas() {
		return graficoDemandasSistemas;
	}
	
	public PieChartModel getGraficoDemandasCliente() {
		return graficoDemandasCliente;
	}

}
