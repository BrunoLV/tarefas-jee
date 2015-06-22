package com.valhala.tarefa.web.managedbeans;

import java.io.InputStream;
import java.io.Serializable;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.valhala.tarefa.ejb.RelatorioEJB;
import com.valhala.tarefa.exceptions.ArquivoUtilException;
import com.valhala.tarefa.file.LeitorArquivo;
import com.valhala.tarefa.relatorio.RelatorioVO;

/**
 * ManagedBean reponsavel pela interação de tela que envolve a emissão de
 * relatórios.
 *
 * @author Bruno Luiz Viana
 * @version 1.0
 * @since 23/06/2014
 */
@Named("RelatorioMB")
@RequestScoped
public class RelatorioMB extends BaseMB implements Serializable {

    private static final long serialVersionUID = 1L;

    private StreamedContent content;

    @EJB
    private RelatorioEJB relatorioService;

    public RelatorioMB() {
    } // fim do método construtor

    /**
     * Método utilizado para emissão do relatório de tarefas completo.
     * @return
     */
    public StreamedContent gerarRelatorioTarefasCompleto() {
        try {
            RelatorioVO relatorioVO = this.relatorioService.gerarRelatorioTarefasCompleto();
            if (relatorioVO.isArquivoGerado()) {
            	InputStream inputStream = LeitorArquivo.obterStreamDeArquivo(relatorioVO.getCaminho(), relatorioVO.getNome());
                content = new DefaultStreamedContent(inputStream, "application/vnd.ms-excel", relatorioVO.getNome());
			} else {
				inserirMensagemDeErro("Arquivo não foi gerado. Verifique com o administrador do sistema.");
			} // fim do bloco try/catch
        } catch (ArquivoUtilException e) {
            inserirMensagemDeErro(e.getMessage());
        } // fim do bloco try/catch
        return content;
    } // fim do método gerarRelatorioTarefasCompleto

    public StreamedContent getContent() {
        return content;
    }

} // fim da classe RelatorioBean
