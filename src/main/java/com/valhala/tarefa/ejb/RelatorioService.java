package com.valhala.tarefa.ejb;

import com.valhala.tarefa.dao.api.TarefaDao;
import com.valhala.tarefa.exceptions.ConsultaSemRetornoException;
import com.valhala.tarefa.exceptions.MontagemRelatorioException;
import com.valhala.tarefa.exceptions.ServiceException;
import com.valhala.tarefa.model.Status;
import com.valhala.tarefa.model.Tarefa;
import com.valhala.tarefa.util.MontadorRelatorio;

import javax.ejb.*;
import javax.inject.Inject;
import java.util.*;

/**
 * Classe responsavel pela emissão dos relatórios dentro da aplicação.
 *
 * @author Bruno Luiz Viana
 * @version 1.0
 * @since 23/06/2014
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class RelatorioService {

    private static final String CHAVE_ABERTAS = "ABERTAS";
    private static final String CHAVE_FECHADAS = "FECHADAS";

    @Inject
    private TarefaDao tarefaDao;
    @Inject
    private MontadorRelatorio montadorRelatorio;

    /**
     * Método utilizado para gerar o relatório de tarefas completo que contém todas as tarefas concluídas e em andamento
     * registradas no sistema.
     * Retorna o nome do arquivo que foi gerado em caso de sucesso.
     *
     * @return
     * @throws ServiceException
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public String gerarRelatorioTarefasCompleto() throws ServiceException {

        String caminhoArquivoGerado;

        List<Status> listaStatus = new ArrayList<>(Arrays.asList(Status.values()));
        listaStatus.remove(Status.CONCLUIDO);

        List<Tarefa> tarefasAbertas = null;
        List<Tarefa> tarefasConcluidas = null;
        Map<String, List> tarefas = new HashMap<>();

        try {
            tarefasAbertas = this.tarefaDao.buscarTodasPorStatus(listaStatus);
        } catch (ConsultaSemRetornoException e) {
            // nesse caso não tem problema
        } // fim do bloco try/catch

        listaStatus.removeAll(Arrays.asList(Status.values()));
        listaStatus.add(Status.CONCLUIDO);
        try {
            tarefasConcluidas = this.tarefaDao.buscarTodasPorStatus(listaStatus);
        } catch (ConsultaSemRetornoException e) {
            // nesse caso não tem problema
        } // fim do bloco try/catch

        tarefas.put(CHAVE_ABERTAS, tarefasAbertas);
        tarefas.put(CHAVE_FECHADAS, tarefasConcluidas);

        try {
            caminhoArquivoGerado = montadorRelatorio.montarRelatorioTarefaCompleto(tarefas);
        } catch (MontagemRelatorioException e) {
            throw new ServiceException(e.getMessage(), e);
        } // fim do bloco try/catch

        return caminhoArquivoGerado;
    } // fim do método gerarRelatorioTarefasCompleto

} // fim da classe RelatorioService