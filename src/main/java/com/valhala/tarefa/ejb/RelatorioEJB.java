package com.valhala.tarefa.ejb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;

import com.valhala.tarefa.dao.api.TarefaDao;
import com.valhala.tarefa.model.Status;
import com.valhala.tarefa.model.Tarefa;
import com.valhala.tarefa.relatorio.RelatorioTarefa;
import com.valhala.tarefa.relatorio.RelatorioVO;

/**
 * Classe responsavel pela emissão dos relatórios dentro da aplicação.
 *
 * @author Bruno Luiz Viana
 * @version 1.0
 * @since 23/06/2014
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class RelatorioEJB {

    private static final String CHAVE_ABERTAS = "ABERTAS";
    private static final String CHAVE_FECHADAS = "FECHADAS";

    @Inject private TarefaDao tarefaDao;
    @Inject private RelatorioTarefa relatorio;

    /**
     * Método utilizado para gerar o relatório de tarefas completo que contém
     * todas as tarefas concluídas e em andamento registradas no sistema.
     * Retorna o nome do arquivo que foi gerado em caso de sucesso.
     *
     * @return
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public RelatorioVO gerarRelatorioTarefasCompleto() {
        Map<String, List<Tarefa>> tarefas = new HashMap<>();

        List<Status> listaStatus = new ArrayList<>(Arrays.asList(Status.values()));
        listaStatus.remove(Status.CONCLUIDO);
        List<Tarefa> tarefasAbertas = this.tarefaDao.buscarTodasPorStatus(listaStatus);

        listaStatus.removeAll(Arrays.asList(Status.values()));
        listaStatus.add(Status.CONCLUIDO);
        List<Tarefa> tarefasConcluidas = this.tarefaDao.buscarTodasPorStatus(listaStatus);

        tarefas.put(CHAVE_ABERTAS, tarefasAbertas);
        tarefas.put(CHAVE_FECHADAS, tarefasConcluidas);

        return relatorio.emitir(tarefas);
    } // fim do método gerarRelatorioTarefasCompleto

} // fim da classe RelatorioService
