package com.valhala.tarefa.relatorio;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.valhala.tarefa.model.Tarefa;
import com.valhala.tarefa.util.PropertiesUtil;


public class RelatorioTarefasExcel implements RelatorioTarefa {
	
    private static final String[] ROTULOS_PLANILHA_TAREFA = {"DEMANDA", "TITULO", "TIPO", "PRIORIDADE", "CLIENTE", "SISTEMA", "ABERTURA", "INICIO", "DESENVOLVIMENTO", "HOMOLOGACAO", "FINAL PLANEJADO", "FINAL EFETIVO", "STATUS", "COLABORADOR", "OBSERVACAO"};
    private static final int NUMERO_LINHA_ROTULO = 0;
    private static final SimpleDateFormat SDF = new SimpleDateFormat("dd/MM/yyyy");
    private static final SimpleDateFormat SDF_ARQUIVO = new SimpleDateFormat("dd-MM-yyyy_hh-mm-ss");
    private Properties properties;

    @PostConstruct
    public void init() {
        this.properties = PropertiesUtil.getProperties("tarefas-jee.properties");
    } // fim do método init

	@Override
	public RelatorioVO emitir(Map<String, List<Tarefa>> mapaRelatorio) {
		return montarRelatorio(mapaRelatorio);
	}
	
	private RelatorioVO montarRelatorio(Map<String, List<Tarefa>> mapaRelatorio) {
		String nome = String.format("%s%s%s", properties.getProperty("arquivo.tarefa.prefix"), SDF_ARQUIVO.format(new Date()), properties.getProperty("arquivo.tarefa.extensao"));
        String caminho = properties.getProperty("arquivo.caminho");
		String nomeInteiro = String.format("%s%s", properties.getProperty("arquivo.caminho"), nome);
        
        RelatorioVO relatorioVO;
        
        try (OutputStream stream = new FileOutputStream(nomeInteiro)) {
        	XSSFWorkbook workbook = new XSSFWorkbook();
        	montarSheet(workbook, mapaRelatorio);
        	workbook.write(stream);
        	stream.flush();
        	relatorioVO = RelatorioVO.instanciarRelatorio(nome, caminho, true);
        } catch (java.io.IOException e) {
        	relatorioVO = RelatorioVO.instanciarRelatorio(nome, caminho, false);
        } // fim do bloco try/catch
        
        return relatorioVO;
	}
	
	private void montarSheet(XSSFWorkbook workbook, Map<String, List<Tarefa>> mapaRelatorio) {
		Set<String> keys = mapaRelatorio.keySet();
		for (String chave : keys) {
			XSSFSheet sheet = workbook.createSheet(chave);
			montarHeader(sheet, RelatorioTarefasExcel.ROTULOS_PLANILHA_TAREFA);
			List<Tarefa> tarefas = mapaRelatorio.get(chave);
			int linha = 1;
			if (tarefas != null && !tarefas.isEmpty()) {
				for (Tarefa tarefa : tarefas) {
					montarLinhaTarefa(tarefa, sheet, linha++);
				} // fim do bloco for
			} else {
				montarAusenciaDados(sheet, linha, properties.getProperty("arquivo.registro.inexistente"));
			} // fim do bloco if/else
		} // fim do bloco for
	} // fim do metodo montarSheet
	
	/*
    Método utilizado para montar a linha de rótulos da planilha.
    */
   private void montarHeader(XSSFSheet sheet, String[] rotulos) {
       XSSFRow linha = sheet.createRow(NUMERO_LINHA_ROTULO);
       int numeroColuna = 0;
       for (String rotulo : rotulos) {
           linha.createCell(numeroColuna++).setCellValue(rotulo);
       } // fim do bloco for
   } // fim do bloco montarLinhaComRotulos

   /*
    Método utilizado para montar as linhas da planilha de tarefas.
    */
   private void montarLinhaTarefa(Tarefa tarefa, XSSFSheet sheet, int numeroLinha) {
       XSSFRow linha = sheet.createRow(numeroLinha);
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
   private void montarAusenciaDados(XSSFSheet sheet, int numeroLinha, String mensagem) {
       XSSFRow linha = sheet.createRow(numeroLinha);
       linha.createCell(0).setCellValue(mensagem);
   } // fim do método monstarLinhaAusenciaResultado

} // fim da classe montarRelatorioExcel
