package com.valhala.tarefa.ejb;

import com.valhala.tarefa.dao.api.*;
import com.valhala.tarefa.exceptions.ConsultaSemRetornoException;
import com.valhala.tarefa.exceptions.DaoException;
import com.valhala.tarefa.exceptions.StreamConverterException;
import com.valhala.tarefa.model.*;
import com.valhala.tarefa.qualifiers.*;
import com.valhala.tarefa.util.PropertiesUtil;
import com.valhala.tarefa.util.StreamConverter;
import com.valhala.tarefa.web.TipoCarga;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.transaction.*;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.*;

/**
 * Enterprise bean utilizado para as cargas assincronas de Tarefas, Sistemas e demais entidades no banco de dados.
 *
 * @author Bruno Luiz Viana
 * @version 1.0
 * @since 04/06/2014
 */
@Stateless
@Auditavel
@TransactionManagement(TransactionManagementType.BEAN)
public class CargaService {

    private static final String CHAVE_INSERIDOS = "Sucesso";
    private static final String CHAVE_NAO_INSERIDOS = "Erro";

    private Properties properties;

    @Inject
    private SistemaDao sistemaDao;
    @Inject
    private ClienteDao clienteDao;
    @Inject
    private EquipeDao equipeDao;
    @Inject
    private TarefaDao tarefaDao;
    @Inject
    private ColaboradorDao colaboradorDao;

    @Resource(mappedName = "java:jboss/mail/Tarefas")
    private Session mailSession;

    @Resource
    private UserTransaction transaction;

    @PostConstruct
    public void inicializar() {
        this.properties = PropertiesUtil.getProperties("tarefas-jee.properties");
    } // fim do método inicializar

    /**
     * Método assincrono utilizado para executar a carga de sistemas no banco de dados.
     *
     * @param stream
     */
    @Asynchronous
    public void executarCargaSistema(@Observes @CargaSistema InputStream stream) {
        Map<String, List<?>> map = new HashMap<String, List<?>>();
        List<Sistema> sistemas;
        List<Sistema> inseridos = new ArrayList<Sistema>();
        List<Sistema> naoInseridos = new ArrayList<Sistema>();
        try {
            sistemas = StreamConverter.converterStreamParaListaDeSistema(stream);
            for (Sistema sistema : sistemas) {
                try {
                    transaction.begin();
                    this.sistemaDao.persistir(sistema);
                    transaction.commit();
                    inseridos.add(sistema);
                } catch (DaoException | SecurityException | IllegalStateException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SystemException | NotSupportedException e) {
                    try {
                        transaction.rollback();
                        continue;
                    } catch (IllegalStateException | SecurityException | SystemException e1) {
                        e1.printStackTrace();
                    } // fim do bloco try/catch
                    naoInseridos.add(sistema);
                } // fim do bloco try/catch
            } // fim do bloco for
            map.put(CargaService.CHAVE_INSERIDOS, inseridos);
            map.put(CargaService.CHAVE_NAO_INSERIDOS, naoInseridos);
            //enviarEmailProcessamentoArquivo(TipoCarga.SISTEMAS, map);
        } catch (StreamConverterException e) {
            //enviarEmailErroArquivo(TipoCarga.SISTEMAS, e.getMessage());
        } // fim do bloco try/catch
    } // fim do método executarCargaSistema

    /**
     * Método assincrono utilizado para executar a carga de clientes no banco de dados.
     *
     * @param stream
     */
    @Asynchronous
    public void executarCargaCliente(@Observes @CargaCliente InputStream stream) {
        Map<String, List<?>> map = new HashMap<String, List<?>>();
        List<Cliente> clientes;
        List<Cliente> inseridos = new ArrayList<Cliente>();
        List<Cliente> naoInseridos = new ArrayList<Cliente>();
        try {
            clientes = StreamConverter.converterStreamParaListaDeClientes(stream);
            for (Cliente cliente : clientes) {
                try {
                    this.transaction.begin();
                    this.clienteDao.persistir(cliente);
                    this.transaction.commit();
                    inseridos.add(cliente);
                } catch (DaoException | NotSupportedException | SystemException | SecurityException | IllegalStateException | RollbackException | HeuristicMixedException | HeuristicRollbackException e) {
                    try {
                        this.transaction.rollback();
                    } catch (IllegalStateException | SecurityException | SystemException e1) {
                        e1.printStackTrace();
                    } // fim do bloco try/catch
                    naoInseridos.add(cliente);
                } // fim do bloco try/catch
            } // fim do bloco for
            map.put(CargaService.CHAVE_INSERIDOS, inseridos);
            map.put(CargaService.CHAVE_NAO_INSERIDOS, naoInseridos);
            //enviarEmailProcessamentoArquivo(TipoCarga.CLIENTES, map);
        } catch (StreamConverterException e) {
            //enviarEmailErroArquivo(TipoCarga.CLIENTES, e.getMessage());
        } // fim do bloco try/catch
    } // fim do bloco executarCargaCliente

    /**
     * Método assincrono utilizado para executar a carga de equipes no banco de dados.
     *
     * @param stream
     */
    @Asynchronous
    public void executarCargaEquipe(@Observes @CargaEquipe InputStream stream) {
        Map<String, List<?>> map = new HashMap<String, List<?>>();
        List<Equipe> equipes;
        List<Equipe> inseridos = new ArrayList<Equipe>();
        List<Equipe> naoInseridos = new ArrayList<Equipe>();
        try {
            equipes = StreamConverter.converterStreamParaListaDeEquipes(stream);
            for (Equipe equipe : equipes) {
                try {
                    this.transaction.begin();
                    equipeDao.persistir(equipe);
                    this.transaction.commit();
                    inseridos.add(equipe);
                } catch (DaoException | NotSupportedException | SystemException | SecurityException | IllegalStateException | RollbackException | HeuristicMixedException | HeuristicRollbackException e) {
                    try {
                        this.transaction.rollback();
                    } catch (IllegalStateException | SecurityException | SystemException e1) {
                        e1.printStackTrace();
                    } // fim do bloco try/catch
                    naoInseridos.add(equipe);
                } // fim do bloco try/catch
            } // fim do bloco for
            map.put(CargaService.CHAVE_INSERIDOS, inseridos);
            map.put(CargaService.CHAVE_NAO_INSERIDOS, naoInseridos);
            //enviarEmailProcessamentoArquivo(TipoCarga.EQUIPES, map);
        } catch (StreamConverterException e) {
            //enviarEmailErroArquivo(TipoCarga.EQUIPES, e.getMessage());
        } // fim do método try/catch
    } // fim do método executarCargaEquipe

    /**
     * Método assincrono utilizado para executar a carga de colaboradores no banco de dados.
     *
     * @param stream
     */
    @Asynchronous
    public void executarCargaColaborador(@Observes @CargaColaborador InputStream stream) {
        Map<String, List<?>> map = new HashMap<String, List<?>>();
        List<Colaborador> colaboradores;
        List<Colaborador> inseridos = new ArrayList<Colaborador>();
        List<Colaborador> naoInseridos = new ArrayList<Colaborador>();
        try {
            colaboradores = StreamConverter.converterStreamParaListaDeColaboradores(stream);
            for (Colaborador colaborador : colaboradores) {
                colaborador.setSenha(colaborador.getMatricula());
                try {
                    this.transaction.begin();
                    this.colaboradorDao.persistir(colaborador);
                    this.transaction.commit();
                    inseridos.add(colaborador);
                } catch (DaoException | NotSupportedException | SystemException | SecurityException | IllegalStateException | RollbackException | HeuristicMixedException | HeuristicRollbackException e) {
                    try {
                        this.transaction.rollback();
                    } catch (IllegalStateException | SecurityException | SystemException e1) {
                        e1.printStackTrace();
                    } // fim do bloco try/catch
                    naoInseridos.add(colaborador);
                } // fim do bloco try/catch
            } // fim do bloco for
            map.put(CargaService.CHAVE_INSERIDOS, inseridos);
            map.put(CargaService.CHAVE_NAO_INSERIDOS, naoInseridos);
            //enviarEmailProcessamentoArquivo(TipoCarga.COLABORADORES, map);
        } catch (StreamConverterException e) {
            //enviarEmailErroArquivo(TipoCarga.COLABORADORES, e.getMessage());
        } // fim do bloco try/catch
    } // fim do método executarCargaColaborador

    /**
     * Método assincrono utilizado para executar a carga de tarefas no banco de dados.
     *
     * @param stream
     */
    @Asynchronous
    public void executarCargaTarefas(@Observes @CargaTarefa InputStream stream) {
        Map<String, List<?>> map = new HashMap<String, List<?>>();
        List<Tarefa> inseridos = new ArrayList<Tarefa>();
        List<Tarefa> naoInseridos = new ArrayList<Tarefa>();
        List<Tarefa> tarefas;
        try {
            tarefas = StreamConverter.converterStreamParaListaDeTarefas(stream);
            for (Tarefa tarefa : tarefas) {
                try {
                    this.transaction.begin();
                    tarefa.setColaborador(tarefa.getColaborador() != null ? this.colaboradorDao.buscarPorNome(tarefa.getColaborador().getNome()) : null);
                    tarefa.setEquipe(tarefa.getEquipe() != null ? this.equipeDao.buscarPorNome(tarefa.getEquipe().getNome()) : null);
                    this.tarefaDao.persistir(tarefa);
                    this.transaction.commit();
                    inseridos.add(tarefa);
                } catch (ConsultaSemRetornoException | DaoException | NotSupportedException | SystemException | SecurityException | IllegalStateException | RollbackException | HeuristicMixedException | HeuristicRollbackException e) {
                    try {
                        System.out.println("Tarefa " + tarefa.getNumeroDemanda() + " não inserida.");
                        this.transaction.rollback();
                        continue;
                    } catch (IllegalStateException | SecurityException | SystemException e1) {
                        e1.printStackTrace();
                    } // fim do bloco try/catch
                    naoInseridos.add(tarefa);
                } // fim do bloco try/catch
            } // fim do bloco for
            map.put(CargaService.CHAVE_INSERIDOS, inseridos);
            map.put(CargaService.CHAVE_NAO_INSERIDOS, naoInseridos);
            //enviarEmailProcessamentoArquivo(TipoCarga.TAREFAS, map);
        } catch (StreamConverterException e) {
            //enviarEmailErroArquivo(TipoCarga.TAREFAS, e.getMessage());
        } // fim do bloco try/catch
    } // fim do método executarCargaTarefas

    /*
    Método utilizado para enviar email de erro de arquivo.
     */
    private void enviarEmailErroArquivo(TipoCarga carga, String erro) {
        MimeMessage message = new MimeMessage(mailSession);
        try {
            Address destinatario = new InternetAddress(this.properties.getProperty("email.destinatario"));
            //Address remetente = new InternetAddress(this.properties.getProperty("email.remetente"));
            message.setRecipient(Message.RecipientType.TO, destinatario);
            //message.setFrom(remetente);
            message.setSubject(this.properties.getProperty("email.assunto.emailErro"));
            message.setSentDate(new Date());
            message.setContent(MessageFormat.format(this.properties.getProperty("email.carga.processamento.erro"), carga, erro), "text/html");
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        } // fim do bloco try/catch
    } // fim do método enviarEmailErroArquivo

    /*
    Método utilizado para enviar email de erro de processamento de arquivo.
     */
    private void enviarEmailProcessamentoArquivo(TipoCarga tipoCarga, Map<String, List<?>> mapa) {
        MimeMessage message = new MimeMessage(mailSession);
        try {
            Address destinatario = new InternetAddress(this.properties.getProperty("email.destinatario"));
            message.setRecipient(Message.RecipientType.TO, destinatario);
            message.setSubject(this.properties.getProperty("email.assunto.emailCarga"));
            message.setSentDate(new Date());
            StringBuffer buffer = new StringBuffer();
            buffer.append(this.properties.getProperty("email.carga.titulo.sucesso"));
            switch (tipoCarga) {
                case TAREFAS:
                    montarCorpoEmailTarefas(mapa, buffer);
                    break;
                case EQUIPES:
                    montarCorpoEmailEquipes(mapa, buffer);
                    break;
                case CLIENTES:
                    montarCorpoEmailClientes(mapa, buffer);
                    break;
                case SISTEMAS:
                    montarCorpoEmailSistemas(mapa, buffer);
                    break;
                case COLABORADORES:
                    montarCorpoEmailColaboradores(mapa, buffer);
                    break;
                default:
                    break;
            } // fim do bloco switch/case
            message.setContent(buffer.toString(), "text/html");
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        } // fim do bloco try/catch
    } // fim do método enviarEmailProcessamentoArquivo

    /*
    Método utilizado para montar o email do processamento de Tarefas.
     */
    @SuppressWarnings("unchecked")
    private void montarCorpoEmailTarefas(Map<String, List<?>> mapa, StringBuffer buffer) {
        List<Tarefa> tarefas;
        tarefas = (List<Tarefa>) mapa.get(CargaService.CHAVE_INSERIDOS);
        buffer.append(MessageFormat.format(this.properties.getProperty("email.corpo.titulo.sucesso"), "Tarefas"));
        if (!tarefas.isEmpty()) {
            incluirTarefaNaString(buffer, tarefas);
        } else {
            buffer.append(MessageFormat.format(this.properties.getProperty("email.corpo.texto.comerro"), "tarefa"));
        } // fim do bloco if/else
        buffer.append(MessageFormat.format(this.properties.getProperty("email.corpo.titulo.erro"), "Tarefas"));
        tarefas = (List<Tarefa>) mapa.get(CargaService.CHAVE_NAO_INSERIDOS);
        if (!tarefas.isEmpty()) {
            incluirTarefaNaString(buffer, tarefas);
        } else {
            buffer.append(MessageFormat.format(this.properties.getProperty("email.corpo.texto.semerro"), "tarefa"));
        } // fim do bloco if/else
    } // fim do método montarCorpoEmailTarefas

    /*
    Método utilizado para incluir a tarefa na String que será inserida no texto.
     */
    private void incluirTarefaNaString(StringBuffer buffer, List<Tarefa> tarefas) {
        for (Tarefa tarefa : tarefas) {
            buffer.append(tarefa.getNumeroDemanda()).append("<br/>");
        } // fim do for
    } // fim do método incluirTarefaNaString

    @SuppressWarnings("unchecked")
    private void montarCorpoEmailSistemas(Map<String, List<?>> mapa, StringBuffer buffer) {
        List<Sistema> sistemas;
        sistemas = (List<Sistema>) mapa.get(CargaService.CHAVE_INSERIDOS);
        buffer.append(MessageFormat.format(this.properties.getProperty("email.corpo.titulo.sucesso"), "Sistemas"));
        if (!sistemas.isEmpty()) {
            incluirSistemaNaString(buffer, sistemas);
        } else {
            buffer.append(MessageFormat.format(this.properties.getProperty("email.corpo.texto.comerro"), "sistema"));
        } // fim do blovo if/else
        buffer.append(MessageFormat.format(this.properties.getProperty("email.corpo.titulo.erro"), "Sistemas"));
        sistemas = (List<Sistema>) mapa.get(CargaService.CHAVE_NAO_INSERIDOS);
        if (!sistemas.isEmpty()) {
            incluirSistemaNaString(buffer, sistemas);
        } else {
            buffer.append(MessageFormat.format(this.properties.getProperty("email.corpo.texto.semerro"), "sistema"));
        } // fim do bloco try/catch
    } // fim do método montarCorpoEmailSistemas

    private void incluirSistemaNaString(StringBuffer buffer,
                                        List<Sistema> sistemas) {
        for (Sistema sistema : sistemas) {
            buffer.append(sistema.getNome()).append("<br/>");
        } //fim do bloco for
    } // fim da classe incluirSistemaNaString

    /*
    Método utilizado para montar o email do processamento de clientes.
     */
    @SuppressWarnings("unchecked")
    private void montarCorpoEmailClientes(Map<String, List<?>> mapa, StringBuffer buffer) {
        List<Cliente> clientes;
        clientes = (List<Cliente>) mapa.get(CargaService.CHAVE_INSERIDOS);
        buffer.append(MessageFormat.format(this.properties.getProperty("email.corpo.titulo.sucesso"), "Clientes"));
        if (!clientes.isEmpty()) {
            incluirClienteNaString(buffer, clientes);
        } else {
            buffer.append(MessageFormat.format(this.properties.getProperty("email.corpo.texto.comerro"), "cliente"));
        } // fim do blovo if/else
        buffer.append(MessageFormat.format(this.properties.getProperty("email.corpo.titulo.erro"), "Clientes"));
        clientes = (List<Cliente>) mapa.get(CargaService.CHAVE_NAO_INSERIDOS);
        if (!clientes.isEmpty()) {
            incluirClienteNaString(buffer, clientes);
        } else {
            buffer.append(MessageFormat.format(this.properties.getProperty("email.corpo.texto.semerro"), "cliente"));
        } // fim do bloco try/catch
    } // fim do método montarCorpoEmailClientes

    /*
    Método utilizado para incluir cliente na String que será inserida no texto.
     */
    private void incluirClienteNaString(StringBuffer buffer, List<Cliente> clientes) {
        for (Cliente Cliente : clientes) {
            buffer.append(Cliente.getNome()).append("<br/>");
        } //fim do bloco for
    } // fim do método incluirClienteNaString

    /*
    Método utilizado para monstar o corpo do email do processamento de equipes.
     */
    @SuppressWarnings("unchecked")
    private void montarCorpoEmailEquipes(Map<String, List<?>> mapa, StringBuffer buffer) {
        List<Equipe> equipes;
        equipes = (List<Equipe>) mapa.get(CargaService.CHAVE_INSERIDOS);
        buffer.append(MessageFormat.format(this.properties.getProperty("email.corpo.titulo.sucesso"), "Equipes"));
        if (!equipes.isEmpty()) {
            incluirEquipeNaString(buffer, equipes);
        } else {
            buffer.append(MessageFormat.format(this.properties.getProperty("email.corpo.texto.comerro"), "equipe"));
        } // fim do bloco if/else
        buffer.append(MessageFormat.format(this.properties.getProperty("email.corpo.titulo.erro"), "Equipes"));
        equipes = (List<Equipe>) mapa.get(CargaService.CHAVE_NAO_INSERIDOS);
        if (!equipes.isEmpty()) {
            incluirEquipeNaString(buffer, equipes);
        } else {
            buffer.append(MessageFormat.format(this.properties.getProperty("email.corpo.texto.semerro"), "equipe"));
        } // fim do bloco try/catch
    } // fim do método montarCorpoEmailEquipes

    /*
    Método utilizado para incluir uma equipe na String que será inserida no texto.
     */
    private void incluirEquipeNaString(StringBuffer buffer, List<Equipe> equipes) {
        for (Equipe equipe : equipes) {
            buffer.append(equipe.getNome()).append("<br/>");
        } //fim do bloco for
    } // fim do método incluirEquipeNaString

    /*
    Método utilizado para montar o email do processamento de colaboradores.
     */
    @SuppressWarnings("unchecked")
    private void montarCorpoEmailColaboradores(Map<String, List<?>> mapa, StringBuffer buffer) {
        List<Colaborador> colaboradores;
        colaboradores = (List<Colaborador>) mapa.get(CargaService.CHAVE_INSERIDOS);
        buffer.append(MessageFormat.format(this.properties.getProperty("email.corpo.titulo.sucesso"), "Colaboradores"));
        if (!colaboradores.isEmpty()) {
            incluirColaboradorNaString(buffer, colaboradores);
        } else {
            buffer.append(MessageFormat.format(this.properties.getProperty("email.corpo.texto.comerro"), "colaborador"));
        } // fim do bloco if/else
        buffer.append(MessageFormat.format(this.properties.getProperty("email.corpo.titulo.erro"), "Colaboradores"));
        colaboradores = (List<Colaborador>) mapa.get(CargaService.CHAVE_NAO_INSERIDOS);
        if (!colaboradores.isEmpty()) {
            incluirColaboradorNaString(buffer, colaboradores);
        } else {
            buffer.append(MessageFormat.format(this.properties.getProperty("email.corpo.texto.semerro"), "colaborador"));
        } // fim do bloco try/catch
    } // fim do método montarCorpoEmailColaboradors

    /*
    Método utilizado para incluir uma lista de de colaboradores na String utilizada como texto no envio do email.
     */
    private void incluirColaboradorNaString(StringBuffer buffer, List<Colaborador> colaboradores) {
        for (Colaborador Colaborador : colaboradores) {
            buffer.append(Colaborador.getNome()).append("<br/>");
        } //fim do bloco for
    } // fim do método incluirColaboradorNaString

} // fim do método CargaService