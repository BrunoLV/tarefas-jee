package com.valhala.tarefa.util;

import com.valhala.tarefa.exceptions.MontagemRelatorioException;
import com.valhala.tarefa.model.Tarefa;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import javax.annotation.PostConstruct;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by Bruno Luiz Viana on 07/06/2014.
 */
public class MontadorRelatorio {

    private Properties properties;

    private static final String[] ROTULOS_PLANILHA_TAREFA = {"DEMANDA", "TITULO", "TIPO", "PRIORIDADE", "CLIENTE", "SISTEMA", "ABERTURA", "INICIO", "FINAL PLANEJADO", "FINAL EFETIVO", "STATUS", "COLABORADOR", "OBSERVACAO"};
    private static final int NUMERO_LINHA_ROTULO = 0;
    private static final SimpleDateFormat SDF = new SimpleDateFormat("dd/MM/yyyy");
    private static final SimpleDateFormat SDF_ARQUIVO = new SimpleDateFormat("dd-MM-yyyy_hh-mm-ss");

    @PostConstruct
    public void init() {
        this.properties = PropertiesUtil.getProperties("tarefas-jee.properties");
    }

    public String montarRelatorioTarefaCompleto(Map<String, List<Tarefa>> mapa) throws MontagemRelatorioException {
        String nomeArquivo = String.format("%s%s%s", properties.getProperty("arquivo.tarefa.prefix"), SDF_ARQUIVO.format(new Date()), properties.getProperty("arquico.tarefa.extensao"));
        String nomeArquivoInteiro = String.format("%s%s", properties.getProperty("arquivo.caminho"), nomeArquivo);
        try (OutputStream stream = new FileOutputStream(nomeArquivoInteiro)) {
            HSSFWorkbook workbook = new HSSFWorkbook();
            montarSheets(workbook, mapa);
            workbook.write(stream);
            stream.flush();
        } catch (java.io.IOException e) {
            throw new MontagemRelatorioException(String.format("Ocorreu erro: %s", e.getMessage()), e);
        } // fim do bloco try/catch
        return nomeArquivo;
    } // fim do método MontadorRelatorio

    private void montarSheets(HSSFWorkbook workbook, Map<String, List<Tarefa>> sheets) {
        for (String key : sheets.keySet()) {
            HSSFSheet sheet = workbook.createSheet(key);
            montarLinhaComRotulos(sheet, ROTULOS_PLANILHA_TAREFA);
            List<Tarefa> lista = sheets.get(key);
            if (lista != null && !lista.isEmpty()) {
                int numeroLinha = NUMERO_LINHA_ROTULO + 1;
                for (Tarefa tarefa : lista) {
                    montarLinhaTarefa(tarefa, sheet, numeroLinha);
                    numeroLinha++;
                } // fim do bloco for
            } else {
                montarLinhaAusenciaResultado(sheet, NUMERO_LINHA_ROTULO + 1, "Não constam tarefas.");
            } // fim do bloco if/else
        } // fim do bloco for
    } // fim do método montarSheets

    private void montarLinhaComRotulos(HSSFSheet sheet, String[] rotulos) {
        HSSFRow linha = sheet.createRow(NUMERO_LINHA_ROTULO);
        int numeroColuna = 0;
        for (String rotulo : rotulos) {
            linha.createCell(numeroColuna++).setCellValue(rotulo);
        } // fim do bloco for
    } // fim do bloco montarLinhaComRotulos

    private void montarLinhaTarefa(Tarefa tarefa, HSSFSheet sheet, int numeroLinha) {
        HSSFRow linha = sheet.createRow(numeroLinha);
        linha.createCell(0).setCellValue(tarefa.getNumeroDemanda());
        linha.createCell(1).setCellValue(tarefa.getTitulo());
        linha.createCell(2).setCellValue(tarefa.getTipoDemanda().getNomeExibicao());
        linha.createCell(3).setCellValue(tarefa.getPrioridade().getNomeExibicao());
        linha.createCell(4).setCellValue(tarefa.getCliente() != null ? tarefa.getCliente().getNome() : "Não consta");
        linha.createCell(5).setCellValue(tarefa.getSistema() != null ? tarefa.getSistema().getNome() : "Não consta");
        linha.createCell(6).setCellValue(tarefa.getAbertura() != null ? SDF.format(tarefa.getAbertura()) : "Não consta");
        linha.createCell(7).setCellValue(tarefa.getInicio() != null ? SDF.format(tarefa.getInicio()) : "Não consta");
        linha.createCell(8).setCellValue(tarefa.getFinalPlanejado() != null ? SDF.format(tarefa.getFinalPlanejado()) : "Não consta");
        linha.createCell(9).setCellValue(tarefa.getFinalEfetivo() != null ? SDF.format(tarefa.getFinalEfetivo()) : "Não consta");
        linha.createCell(10).setCellValue(tarefa.getStatus().getNomeExibicao());
        linha.createCell(11).setCellValue(tarefa.getColaborador() != null ? tarefa.getColaborador().getNome() : "Não consta");
        linha.createCell(12).setCellValue(tarefa.getObservacao() != null && !tarefa.getObservacao().trim().equals("") ? tarefa.getObservacao() : "");
    } // fim do método monstarLinhaTarefa

    private void montarLinhaAusenciaResultado(HSSFSheet sheet, int numeroLinha, String mensagem) {
        HSSFRow linha = sheet.createRow(numeroLinha);
        linha.createCell(0).setCellValue(mensagem);
    }

}
