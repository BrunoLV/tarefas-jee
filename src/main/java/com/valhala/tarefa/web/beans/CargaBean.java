package com.valhala.tarefa.web.beans;

import com.valhala.tarefa.qualifiers.*;
import com.valhala.tarefa.web.TipoCarga;
import org.primefaces.model.UploadedFile;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

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

    /*
    Injeção dos eventos que serão disparados para cada tipo especifico de carga solicitado pelo usuário.
     */
    @Inject
    @CargaSistema
    private Event<InputStream> eventoCargaSistema;

    @Inject
    @CargaCliente
    private Event<InputStream> eventoCargaCliente;

    @Inject
    @CargaEquipe
    private Event<InputStream> eventoCargaEquipe;

    @Inject
    @CargaTarefa
    private Event<InputStream> eventoCargaTarefa;

    @Inject
    @CargaColaborador
    private Event<InputStream> eventoCargaColaborador;

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
                    eventoCargaSistema.fire(this.file.getInputstream());
                    break;
                case CLIENTES:
                    eventoCargaCliente.fire(this.file.getInputstream());
                    break;
                case EQUIPES:
                    eventoCargaEquipe.fire(this.file.getInputstream());
                    break;
                case TAREFAS:
                    eventoCargaTarefa.fire(this.file.getInputstream());
                    break;
                case COLABORADORES:
                    eventoCargaColaborador.fire(this.file.getInputstream());
                    break;
                default:
                    break;
            } // fim do bloco switch
            inserirMensagemDeSucesso("A carga foi remetida. No final você receberá um email com o resultado.");
        } catch (IOException e) {
            inserirMensagemDeErro(String.format("Ocorreu um erro durante a execução da ação: %s", e.getMessage()));
        } // fim do bloco try/catch
    } // fim do método upload

} // fim da classe CargaBean