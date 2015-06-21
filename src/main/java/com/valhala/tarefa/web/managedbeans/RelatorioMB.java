package com.valhala.tarefa.web.managedbeans;

import java.io.InputStream;
import java.io.Serializable;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.valhala.tarefa.ejb.RelatorioEJB;
import com.valhala.tarefa.exceptions.ArquivoUtilException;
import com.valhala.tarefa.exceptions.ServiceException;
import com.valhala.tarefa.file.LeitorArquivo;
import com.valhala.tarefa.util.PropertiesUtil;

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

    private Properties properties;
    private StreamedContent content;

    @EJB
    private RelatorioEJB relatorioService;

    public RelatorioMB() {
    } // fim do método construtor

    @PostConstruct
    public void init() {
        properties = PropertiesUtil.getProperties("tarefas-jee.properties");
    } // fim do método init

    /**
     * Método utilizado para emissão do relatório de tarefas completo.
     *
     * @return
     */
    public StreamedContent gerarRelatorioTarefasCompleto() {
        try {
            String nome = this.relatorioService.gerarRelatorioTarefasCompleto();
            InputStream inputStream = LeitorArquivo.obterStreamDeArquivo(properties.getProperty("arquivo.caminho"), nome);
            content = new DefaultStreamedContent(inputStream, "application/vnd.ms-excel", nome);
        } catch (ServiceException | ArquivoUtilException e) {
            inserirMensagemDeErro(e.getMessage());
        } // fim do bloco try/catch
        return content;
    } // fim do método gerarRelatorioTarefasCompleto

    public StreamedContent getContent() {
        return content;
    }

} // fim da classe RelatorioBean
