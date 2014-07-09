package com.valhala.tarefa.web.beans;

import com.valhala.tarefa.ejb.TarefaService;
import com.valhala.tarefa.exceptions.ConsultaSemRetornoException;
import org.primefaces.model.chart.PieChartModel;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
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
public class GraficoBean extends BaseJSFBean {

    private static DateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
    @EJB
    private TarefaService tarefaService;
    private PieChartModel graficoEquipes = new PieChartModel();

    @PostConstruct
    public void init() {
        try {
            gerarGraficoEquipe(calcularDataInicial(), calcularDataFinal(), "Todos");
        } catch (ParseException e) {
            inserirMensagemDeErro("Ocorreu um erro de parseamento de datas.");
        } // fim do bloco try/catch
    } // fim do método init

    public void gerarGraficoEquipe(Date inicio, Date fim, String tipo) {
        graficoEquipes = new PieChartModel();
        try {
            Map<String, BigInteger> mapa = this.tarefaService.buscarTotaisPorEquipeEPeriodoETipo(inicio, fim, tipo);
            Set<String> chaves = mapa.keySet();
            for (String chave : chaves) {
                graficoEquipes.set(chave, mapa.get(chave).intValue());
            } // fim do bloco for
        } catch (ConsultaSemRetornoException e) {
            inserirMensagemDeErro(e.getMessage());
        } // fim do método
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

    public PieChartModel getGraficoEquipes() {
        return graficoEquipes;
    }

}
