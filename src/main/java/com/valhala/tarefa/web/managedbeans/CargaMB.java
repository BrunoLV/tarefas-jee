package com.valhala.tarefa.web.managedbeans;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import org.primefaces.model.UploadedFile;

import com.valhala.tarefa.ejb.CargaEJB;
import com.valhala.tarefa.exceptions.StreamConverterException;
import com.valhala.tarefa.file.carga.ConversorCarga;
import com.valhala.tarefa.file.carga.TipoCarga;
import com.valhala.tarefa.file.converter.ClienteStreamConverter;
import com.valhala.tarefa.file.converter.ColaboradorStreamConverter;
import com.valhala.tarefa.file.converter.EquipeStreamConverter;
import com.valhala.tarefa.file.converter.SistemaStreamConverter;
import com.valhala.tarefa.file.converter.TarefaStreamConverter;

/**
 * ManagedBean reponsavel pela interação de tela que envolve as ações de carga
 * de dados dentro do sistema.
 *
 * @author Bruno Luiz Viana
 * @version 1.0
 * @since 23/06/2014
 */
@Named("CargaMB")
@RequestScoped
public class CargaMB extends BaseMB implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private CargaEJB cargaService;

    // Objeto que representa o arquivo que foi enviado para o servidor.
    private UploadedFile file;

    // Tipo de carga solicitado pelo usuario.
    private TipoCarga tipoCarga;

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public TipoCarga getTipoCarga() {
        return tipoCarga;
    }

    public void setTipoCarga(TipoCarga tipoCarga) {
        this.tipoCarga = tipoCarga;
    }

    public TipoCarga[] tiposCarga() {
        return TipoCarga.values();
    }

    /**
     * Método que dispara a ação de carga de informação contidas no arquivo para
     * dentro da base do sistema.
     */
    public void upload() {
        try {
            InputStream stream = this.file.getInputstream();
            switch (this.tipoCarga) {
                case TAREFAS:
                    this.cargaService.executarCarga(
                            ConversorCarga.efetuarConversaoParaCarga(stream, TarefaStreamConverter.obterConversorStream()),
                            this.tipoCarga);
                    break;
                case SISTEMAS:
                    this.cargaService.executarCarga(
                            ConversorCarga.efetuarConversaoParaCarga(stream, SistemaStreamConverter.obterConversorStream()),
                            this.tipoCarga);
                    break;
                case CLIENTES:
                    this.cargaService.executarCarga(
                            ConversorCarga.efetuarConversaoParaCarga(stream, ClienteStreamConverter.obterConversorStream()),
                            this.tipoCarga);
                    break;
                case EQUIPES:
                    this.cargaService.executarCarga(
                            ConversorCarga.efetuarConversaoParaCarga(stream, EquipeStreamConverter.obterConversorStream()),
                            this.tipoCarga);
                    break;
                case COLABORADORES:
                    this.cargaService.executarCarga(
                            ConversorCarga.efetuarConversaoParaCarga(stream, ColaboradorStreamConverter.obterConversorStream()),
                            this.tipoCarga);
                    break;
                default:
                    break;
            } // fim do bloco switch
            inserirMensagemDeSucesso("A carga foi remetida foi efetuada com sucesso.");
        } catch (IOException | StreamConverterException e) {
            inserirMensagemDeErro(String.format("Ocorreu um erro durante a execução da ação: %s", e.getMessage()));
        } // fim do bloco try/catch
    } // fim do método upload

} // fim da classe CargaBean
