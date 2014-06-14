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

@Named("CargaBean")
@RequestScoped
public class CargaBean extends BaseJSFBean implements Serializable {

    private static final long serialVersionUID = 1L;

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

    private UploadedFile file;
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
            }
            inserirMensagemDeSucesso("A carga foi remetida. No final você receberá um email com o resultado.");
        } catch (IOException e) {
            inserirMensagemDeErro(String.format("Ocorreu um erro durante a execução da ação: %s", e.getMessage()));
        }
    }


}
