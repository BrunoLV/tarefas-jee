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
 * Classe utilizada para montar os relatórios que podem ser emitidos na aplicação.
 *
 * @author Bruno Luiz Viana
 * @version 1.0
 * @since 23/06/2014
 */
public class MontadorRelatorio {

private static final String[] ROTULOS_PLANILHA_TAREFA = {"DEMANDA", "TITULO", "TIPO", "PRIORIDADE", "CLIENTE", "SISTEMA", "ABERTURA", "INICIO", "DESENVOLVIMENTO", "HOMOLOGACAO", "FINAL PLANEJADO", "FINAL EFETIVO", "STATUS", "COLABORADOR", "OBSERVACAO"};
    private static final int NUMERO_LINHA_ROTULO = 0;
    private static final SimpleDateFormat SDF = new SimpleDateFormat("dd/MM/yyyy");
    private static final SimpleDateFormat SDF_ARQUIVO = new SimpleDateFormat("dd-MM-yyyy_hh-mm-ss");
    private Properties properties;

    @PostConstruct
    public void init() {
        this.properties = PropertiesUtil.getProperties("tarefas-jee.properties");
    } // fim do método init

    /**
     * Método utilizado para emitir o relatório de tarefas completo.
     * Esse método gera um excel que contém todas as tarefas em andamento e as concluidas.
     *
     * @param mapa
     * @return
     * @throws MontagemRelatorioException
     */
    @SuppressWarnings("rawtypes")
	public String montarRelatorioTarefaCompleto(Map<String, List> mapa) throws MontagemRelatorioException {
        String nomeArquivo = String.format("%s%s%s", properties.getProperty("arquivo.tarefa.prefix"), SDF_ARQUIVO.format(new Date()), properties.getProperty("arquivo.tarefa.extensao"));
        String nomeArquivoInteiro = String.format("%s%s", properties.getProperty("arquivo.caminho"), nomeArquivo);
        try (OutputStream stream = new FileOutputStream(nomeArquivoInteiro)) {
            HSSFWorkbook workbook = new HSSFWorkbook();
            montarSheets(workbook, mapa, ROTULOS_PLANILHA_TAREFA, TipoRelatorio.TAREFAS);
            workbook.write(stream);
            stream.flush();
        } catch (java.io.IOException e) {
            throw new MontagemRelatorioException(String.format("Ocorreu erro: %s", e.getMessage()), e);
        } // fim do bloco try/catch
        return nomeArquivo;
    } // fim do método MontadorRelatorio

    /*
    Método utilizado para montar as planilhas dentro da pasta do arquivo de relatório.
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	private void montarSheets(HSSFWorkbook workbook, Map<String, List> sheets, String[] rotulos, TipoRelatorio tipo) {
        for (String key : sheets.keySet()) {
            HSSFSheet sheet = workbook.createSheet(key);
            montarLinhaComRotulos(sheet, rotulos);
            List<Object> lista = sheets.get(key);
            switch (tipo) {
                case TAREFAS:
                    montarSheetTarefa(lista, sheet);
                    break;
                case CLIENTE:
                	break;
                case COLABORADORES:
                	break;
                case EQUIPE:
                	break;
                case SISTEMAS:
                	break;
                default:
                	break;
            } // fim do bloco switch
        } // fim do bloco for
    } // fim do método monstarSheets

    /*
    Método utilizado para montar as planilhas na pasta do relatório de Tarefas
     */
    private void montarSheetTarefa(List<Object> tarefas, HSSFSheet sheet) {
        if (tarefas != null && !tarefas.isEmpty()) {
            int numeroLinha = NUMERO_LINHA_ROTULO + 1;
            for (Object tarefa : tarefas) {
                montarLinhaTarefa((Tarefa) tarefa, sheet, numeroLinha);
                numeroLinha++;
            } // fim do bloco for
        } else {
            montarLinhaAusenciaResultado(sheet, NUMERO_LINHA_ROTULO + 1, properties.getProperty("arquivo.registro.inexistente"));
        } // fim do bloco if/else
    } // fim do método montarSheets

    /*
    Método utilizado para montar a linha de rótulos da planilha.
     */
    private void montarLinhaComRotulos(HSSFSheet sheet, String[] rotulos) {
        HSSFRow linha = sheet.createRow(NUMERO_LINHA_ROTULO);
        int numeroColuna = 0;
        for (String rotulo : rotulos) {
            linha.createCell(numeroColuna++).setCellValue(rotulo);
        } // fim do bloco for
    } // fim do bloco montarLinhaComRotulos

    /*
    Método utilizado para montar as linhas da planilha de tarefas.
     */
    private void montarLinhaTarefa(Tarefa tarefa, HSSFSheet sheet, int numeroLinha) {
        HSSFRow linha = sheet.createRow(numeroLinha);
        linha.createCell(0).setCellValue(tarefa.getNumeroDemanda());
        linha.createCell(1).setCellValue(tarefa.getTitulo());
        linha.createCell(2).setCellValue(tarefa.getTipoDemanda().getNomeExibicao());
        linha.createCell(3).setCellValue(tarefa.getPrioridade().getNomeExibicao());
        linha.createCell(4).setCellValue(tarefa.getCliente() != null ? tarefa.getCliente().getNome() : properties.getProperty("arquivo.registro.inexistente"));
        linha.createCell(5).setCellValue(tarefa.getSistema() != null ? tarefa.getSistema().getNome() : properties.getProperty("arquivo.registro.inexistente"));
        linha.createCell(6).setCellValue(tarefa.getAbertura() != null ? SDF.format(tarefa.getAbertura()) : properties.getProperty("arquivo.registro.inexistente"));
        linha.createCell(7).setCellValue(tarefa.getInicio() != null ? SDF.format(tarefa.getInicio()) : properties.getProperty("arquivo.registro.inexistente"));
        linha.createCell(8).setCellValue(tarefa.getDesenvolvimento() != null ? SDF.format(tarefa.getDesenvolvimento()) : properties.getProperty("arquivo.registro.inexistente"));
        linha.createCell(9).setCellValue(tarefa.getHomologacao() != null ? SDF.format(tarefa.getHomologacao()) : properties.getProperty("arquivo.registro.inexistente"));
        linha.createCell(10).setCellValue(tarefa.getFinalPlanejado() != null ? SDF.format(tarefa.getFinalPlanejado()) : properties.getProperty("arquivo.registro.inexistente"));
        linha.createCell(11).setCellValue(tarefa.getFinalEfetivo() != null ? SDF.format(tarefa.getFinalEfetivo()) : properties.getProperty("arquivo.registro.inexistente"));
        linha.createCell(12).setCellValue(tarefa.getStatus().getNomeExibicao());
        linha.createCell(13).setCellValue(tarefa.getColaborador() != null ? tarefa.getColaborador().getNome() : properties.getProperty("arquivo.registro.inexistente"));
        linha.createCell(14).setCellValue(tarefa.getObservacao() != null && !tarefa.getObservacao().trim().equals("") ? tarefa.getObservacao() : properties.getProperty("arquivo.registro.inexistente"));
    } // fim do método monstarLinhaTarefa

    /*
    Método utilizado para montar a linha que indica que não a registros para serem mostrados no relatório.
     */
    private void montarLinhaAusenciaResultado(HSSFSheet sheet, int numeroLinha, String mensagem) {
        HSSFRow linha = sheet.createRow(numeroLinha);
        linha.createCell(0).setCellValue(mensagem);
    } // fim do método monstarLinhaAusenciaResultado

} // fim da classe MontadorRelatorio