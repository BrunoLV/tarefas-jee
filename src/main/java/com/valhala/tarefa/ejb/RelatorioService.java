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
 * Created by Bruno Luiz Viana on 07/06/2014.
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

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public String gerarRelatorioTarefasCompleto() throws ServiceException {

        String caminhoArquivoGerado;

        List<Status> listaStatus = new ArrayList<>(Arrays.asList(Status.values()));
        listaStatus.remove(Status.CONCLUIDO);

        List<Tarefa> tarefasAbertas = null;
        List<Tarefa> tarefasConcluidas = null;
        Map<String, List<Tarefa>> tarefas = new HashMap<>();

        try {
            tarefasAbertas = this.tarefaDao.buscarTodasPorStatus(listaStatus);
        } catch (ConsultaSemRetornoException e) {
        }

        listaStatus.removeAll(Arrays.asList(Status.values()));
        listaStatus.add(Status.CONCLUIDO);
        try {
            tarefasConcluidas = this.tarefaDao.buscarTodasPorStatus(listaStatus);
        } catch (ConsultaSemRetornoException e) {
        }

        tarefas.put(CHAVE_ABERTAS, tarefasAbertas);
        tarefas.put(CHAVE_FECHADAS, tarefasConcluidas);

        try {
            caminhoArquivoGerado = montadorRelatorio.montarRelatorioTarefaCompleto(tarefas);
        } catch (MontagemRelatorioException e) {
            throw new ServiceException(e.getMessage(), e);
        }

        return caminhoArquivoGerado;
    }

}
