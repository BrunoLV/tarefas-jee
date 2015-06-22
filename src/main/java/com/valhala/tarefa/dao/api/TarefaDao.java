package com.valhala.tarefa.dao.api;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.valhala.tarefa.model.Colaborador;
import com.valhala.tarefa.model.Status;
import com.valhala.tarefa.model.Tarefa;

/**
 * Interface DAO especializada para a entidade Tarefa.
 *
 * @author Bruno Luiz Viana
 * @version 1.0
 * @since 23/02/2014
 */
public interface TarefaDao extends Dao<Tarefa> {

    /**
     * Método utilizado para buscar todas as tarefas pertencentes a um
     * colaborador especifico.
     *
     * @param colaborador
     * @return
     */
    List<Tarefa> buscarTodasPorColaborador(Colaborador colaborador);

    /**
     * Método utilizado para buscar todas as tarefas pertencentes a um
     * colaborador especifico e que estejam dentro dentro de algum status
     * passado no método.
     *
     * @param colaborador
     * @param status
     * @return
     */
    List<Tarefa> buscarTodasPorColaboradorEStatus(Colaborador colaborador, List<Status> status);

    /**
     * Método utilizado para buscar todas as tarefas que estejem dentro dos
     * status passados para o método.
     *
     * @param status
     * @return
     */
    List<Tarefa> buscarTodasPorStatus(List<Status> status);

    /**
     * Método utilizado para buscar todas tarefas com datas já definidas.
     *
     * @return
     */
    List<Tarefa> buscarTodasComDatasDefinidas();

    /**
     * Método utilizado para buscar todas as tarefas com datas já definidas de
     * um determinado colaborador.
     *
     * @param colaborador
     * @param status
     * @return
     */
    List<Tarefa> buscarTodasPorColaboradorEStatusComDatasDefinidas(Colaborador colaborador, List<Status> status);

    /**
     * Método utilizado para buscar todas as tarefas com datas já definidas
     * dentro de determinados Status.
     *
     * @param status
     * @return
     */
    List<Tarefa> buscarTodasPorStatusComDatasDefinidas(List<Status> status);

    /**
     * Método utilizado para buscar totais de demandas e nome da equipe a qual
     * pertencem filtrando por um periodo definido.
     *
     * @param inicio
     * @param fim
     * @return
     */
    Map<String, BigInteger> buscarTotaisDemandasPorPeriodoDeTodasEquipes(Date inicio, Date fim);

    /**
     * Método utilizado para buscar totais de demandas e no da equipe a qual
     * pertencem filtrando por um periodo definido, por tipo de demanda e
     * equipes.
     *
     * @param inicio
     * @param fim
     * @param tipo
     * @return
     */
    Map<String, BigInteger> buscarTotaisDemandasPorPeriodoEEquipePorTipo(Date inicio, Date fim, String tipo);

    /**
     * Método utilizado para buscar totais de demandas e o nome do sistema a
     * qual pertencem filtandro por um periodo definido.
     *
     * @param inicio
     * @param fim
     * @return
     */
    Map<String, BigInteger> buscarTotaisDemandasTodosSistemas(Date inicio, Date fim);

    /**
     * Método utilizado para buscar totais de demandas e o nome do sistema a
     * qual pertencem filtrando por um periodo definido e equipe.
     *
     * @param inicio
     * @param fim
     * @param id
     * @return
     */
    Map<String, BigInteger> buscarTotaisDemandasTodosSistemasPorEquipe(Date inicio, Date fim, Long id);

    /**
     * Método utilizado para buscar totais de demandas e o nome do sistema a
     * qual pertencem filtrando por um periodo definido e tipo de demanda.
     *
     * @param inicio
     * @param fim
     * @param tipo
     * @return
     */
    Map<String, BigInteger> buscarTotaisDemandasTodosSistemasPorTipo(Date inicio, Date fim, String tipo);

    /**
     * Método utilizado para buscar totais de demandas e o nome do sistema a
     * qual pertencem filtrando por um periodo definido, equipe e tipo de
     * demanda.
     *
     * @param inicio
     * @param fim
     * @param id
     * @param tipo
     * @return
     */
    Map<String, BigInteger> buscarTotaisDemandasTodosSistemasPorTipoEEquipe(Date inicio, Date fim, Long id, String tipo);

    /**
     * Método utilizado para buscar totais de demandas e nome do cliente a qual
     * pertencem filtrando por um periodo definido.
     *
     * @param inicio
     * @param fim
     * @return
     */
    Map<String, BigInteger> buscarTotaisDemandasTodosClientes(Date inicio, Date fim);

    /**
     * Método utilizado para buscar totais de demandas e nome do cliente a qual
     * pertencem filtrando por um periodo definido e tipo de demanda.
     *
     * @param inicio
     * @param fim
     * @param tipo
     * @return
     */
    Map<String, BigInteger> buscarTotaisDemandasTodosClientesPorTipo(Date inicio, Date fim, String tipo);

    /**
     * Método utilizado para buscar totais de demandas e nome do cliente a qual
     * pertencem filtrando por um periodo definido e por equipe.
     *
     * @param inicio
     * @param fim
     * @param id
     * @return
     */
    Map<String, BigInteger> buscarTotaisDemandasTodosClientesPorEquipe(Date inicio, Date fim, Long id);

    /**
     * Método utilizado para buscar totais de demandas e nome do cliente a qual
     * pertencem filtrando por um periodo definido, equipe e tipo de demanda.
     *
     * @param inicio
     * @param fim
     * @param id
     * @param tipo
     * @return
     */
    Map<String, BigInteger> buscarTotaisDemandasTodosClientesPorEquipeETipo(Date inicio, Date fim, Long id, String tipo);

} // fim da interface TarefaDao
