package com.valhala.tarefa.web.beans;

import com.valhala.tarefa.ejb.RelatorioService;
import com.valhala.tarefa.exceptions.ServiceException;
import com.valhala.tarefa.util.PropertiesUtil;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.io.*;
import java.util.Properties;

/**
 * ManagedBean reponsavel pela interação de tela que envolve a emissão de relatórios.
 *
 * @author Bruno Luiz Viana
 * @version 1.0
 * @since 23/06/2014
 */
@Named("RelatorioBean")
@RequestScoped
public class RelatorioBean extends BaseJSFBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private Properties properties;
    private StreamedContent content;

    @EJB
    private RelatorioService relatorioService;

    public RelatorioBean() {
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
            InputStream inputStream = null;
            inputStream = new FileInputStream(new File(String.format("%s%s", properties.getProperty("arquivo.caminho"), nome)));
            content = new DefaultStreamedContent(inputStream, "application/vnd.ms-excel", nome);
        } catch (ServiceException | FileNotFoundException e) {
            inserirMensagemDeErro(e.getMessage());
        } // fim do bloco try/catch
        return content;
    } // fim do método gerarRelatorioTarefasCompleto

    public StreamedContent getContent() {
        return content;
    }

} // fim da classe RelatorioBean