package com.valhala.tarefa.web.beans;

import java.io.IOException;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import org.primefaces.model.UploadedFile;

import com.valhala.tarefa.ejb.CargaService;
import com.valhala.tarefa.exceptions.StreamConverterException;
import com.valhala.tarefa.util.StreamConverter;
import com.valhala.tarefa.web.TipoCarga;

/**
 * ManagedBean reponsavel pela interação de tela que envolve as ações de carga de dados dentro do sistema.
 *
 * @author Bruno Luiz Viana
 * @version 1.0
 * @since 23/06/2014
 */
@Named("CargaBean")
@RequestScoped
public class CargaBean extends BaseJSFBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private CargaService cargaService;

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
     * Método que dispara a ação de carga de informação contidas no arquivo para dentro da base do sistema.
     */
    public void upload() {
        try {
            switch (this.tipoCarga) {
                case SISTEMAS:
                    this.cargaService.executarCargaSistema(StreamConverter.converterStreamParaListaDeSistema(this.file.getInputstream()));
                    break;
                case CLIENTES:
                    this.cargaService.executarCargaCliente(StreamConverter.converterStreamParaListaDeClientes(this.file.getInputstream()));
                    break;
                case EQUIPES:
                    this.cargaService.executarCargaEquipe(StreamConverter.converterStreamParaListaDeEquipes(this.file.getInputstream()));
                    break;
                case TAREFAS:
                	this.cargaService.executarCargaTarefas(StreamConverter.converterStreamParaListaDeTarefas(this.file.getInputstream()));
                    break;
                case COLABORADORES:
                	this.cargaService.executarCargaColaborador(StreamConverter.converterStreamParaListaDeColaboradores(this.file.getInputstream()));
                    break;
                default:
                    break;
            } // fim do bloco switch
            inserirMensagemDeSucesso("A carga foi remetida. No final você recebera um email com o resultado.");
        } catch (IOException | StreamConverterException e) {
            inserirMensagemDeErro(String.format("Ocorreu um erro durante a execução da ação: %s", e.getMessage()));
        } // fim do bloco try/catch
    } // fim do método upload

} // fim da classe CargaBean