package com.valhala.tarefa.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Entity que mapeia a tabela de Colabores no banco de dados.
 *
 * @author Bruno Luiz Viana
 * @version 1.0
 * @since 23/02/2014
 */
@Entity
@Table(name = "tb_tarefa")
@SqlResultSetMappings({
        @SqlResultSetMapping(name = Tarefa.MAPPING_TOTAL_DEMANDAS_POR_EQUIPE, columns = {@ColumnResult(name = "nome"), @ColumnResult(name = "total")}),
        @SqlResultSetMapping(name = Tarefa.MAPPING_TOTAL_DEMANDAS_POR_SISTEMA, columns = {@ColumnResult(name = "nome"), @ColumnResult(name = "total")}),
        @SqlResultSetMapping(name = Tarefa.MAPPING_TOTAL_DEMANDAS_POR_CLIENTE, columns = {@ColumnResult(name = "nome"), @ColumnResult(name = "total")})
}) // fim da declaracao dos SqlResultSetMappings
@NamedQueries({
        @NamedQuery(name = Tarefa.NAMED_QUERY_BUSCAR_TODOS,
        	query = "select t from Tarefa as t"),
        @NamedQuery(name = Tarefa.NAMED_QUERY_BUSCAR_POR_COLABORADOR, 
        	query = "select t from Tarefa as t where t.colaborador = :colaborador"),
        @NamedQuery(name = Tarefa.NAMED_QUERY_BUSCAR_POR_COLABORADOR_E_STATUS, 
        	query = "select t from Tarefa as t where t.colaborador = :colaborador and t.status in (:status)"),
        @NamedQuery(name = Tarefa.NAMED_QUERY_BUSCAR_POR_STATUS, 
        	query = "select t from Tarefa as t where t.status in (:status)"),
        @NamedQuery(name = Tarefa.NAMED_QUERY_BUSCAR_POR_EQUIPE, 
        	query = "select t from Tarefa as t where t.equipe = :equipe"),
        @NamedQuery(name = Tarefa.NAMED_QUERY_BUSCAR_POR_CLIENTE, 
        	query = "select t from Tarefa as t where t.cliente = :cliente"),
        @NamedQuery(name = Tarefa.NAMED_QUERY_BUSCAR_POR_SISTEMA, 
        	query = "select t from Tarefa as t where t.sistema = :sistema"),
        @NamedQuery(name = Tarefa.NAMED_QUERY_BUSCAR_POR_STATUS_DATAS_DEFINIDAS, 
        	query = "select t from Tarefa as t where t.status in (:status) and t.inicio is not null and t.finalPlanejado is not null"),
        @NamedQuery(name = Tarefa.NAMED_QUERY_BUSCAR_TODOS_DATAS_DEFINIDAS, 
        	query = "select t from Tarefa as t where t.inicio is not null and t.finalPlanejado is not null"),
        @NamedQuery(name = Tarefa.NAMED_QUERY_BUSCAR_POR_COLABORADOR_E_STATUS_DATAS_DEFINIDAS, 
        	query = "select t from Tarefa as t where t.colaborador = :colaborador and t.status in (:status) and t.inicio is not null and t.finalPlanejado is not null"),
        @NamedQuery(name = Tarefa.NAMED_QUERY_BUSCAR_POR_EQUIPE_E_DATAS, 
        	query = "select t from Tarefa as t where t.equipe = :equipe and t.abertura between :dataInicio and :dataFim"),
        @NamedQuery(name = Tarefa.NAMED_QUERY_BUSCAR_POR_CLIENTE_E_DATAS, 
        	query = "select t from Tarefa as  t where t.cliente = :cliente and t.abertura between :dataInicio and :dataFim"),
        @NamedQuery(name = Tarefa.NAMED_QUERY_BUSCAR_POR_SISTEMA_E_DATAS, 
        	query = "select t from Tarefa as t where t.sistema = :sistema and t.abertura between :dataInicio and :dataFim")
}) // fim da declaracao de namedqueries
@NamedNativeQueries({
        @NamedNativeQuery(name = Tarefa.NAMED_NATIVE_QUERY_TOTAL_TODAS_EQUIPES_POR_TIPO, 
        	query = "select b.nome_equipe as nome, count(*) as total "
        			+ "from tb_tarefa a, tb_equipe b "
        			+ "where a.data_abertura between ? and ? and a.equipe_id_equipe = b.id_equipe and a.tipo_demanda = ? "
        			+ "group by b.nome_equipe",
            resultSetMapping = Tarefa.MAPPING_TOTAL_DEMANDAS_POR_EQUIPE),
        @NamedNativeQuery(name = Tarefa.NAMED_NATIVE_QUERY_TOTAL_TODAS_EQUIPES, 
        	query = "select b.nome_equipe as nome, count(*) as total "
        			+ "from tb_tarefa a, tb_equipe b "
        			+ "where a.data_abertura between ? and ? and a.equipe_id_equipe = b.id_equipe "
        			+ "group by b.nome_equipe",
            resultSetMapping = Tarefa.MAPPING_TOTAL_DEMANDAS_POR_EQUIPE),
        @NamedNativeQuery(name = Tarefa.NAMED_NATIVE_QUERY_TOTAL_DEMANDAS_TODOS_SISTEMAS, 
        	query = "select b.nome_sistema as nome, count(*) as total "
        			+ "from tb_tarefa a, tb_sistema b "
        			+ "where a.data_abertura between ? and ? and a.sistema_id_sistema = b.id_sistema "
        			+ "group by b.nome_sistema",
        	resultSetMapping = Tarefa.MAPPING_TOTAL_DEMANDAS_POR_SISTEMA),
        @NamedNativeQuery(name = Tarefa.NAMED_NATIVE_QUERY_TOTAL_DEMANDAS_TODOS_SISTEMAS_POR_EQUIPE, 
        	query = "select b.nome_sistema as nome, count(*) as total "
        			+ "from tb_tarefa a, tb_sistema b, tb_equipe c "
        			+ "where a.data_abertura between ? and ? and a.sistema_id_sistema = b.id_sistema and a.equipe_id_equipe = c.id_equipe and c.id_equipe = ? "
        			+ "group by b.nome_sistema",
        	resultSetMapping = Tarefa.MAPPING_TOTAL_DEMANDAS_POR_SISTEMA),
        @NamedNativeQuery(name = Tarefa.NAMED_NATIVE_QUERY_TOTAL_DEMANDAS_TODOS_SISTEMAS_POR_TIPO,
        	query = "select b.nome_sistema as nome, count(*) as total "
        			+ "from tb_tarefa a, tb_sistema b "
        			+ "where a.data_abertura between ? and ? and a.sistema_id_sistema = b.id_sistema and a.tipo_demanda = ?"
        			+ "group by b.nome_sistema",
        	resultSetMapping = Tarefa.MAPPING_TOTAL_DEMANDAS_POR_SISTEMA),
        @NamedNativeQuery(name = Tarefa.NAMED_NATIVE_QUERY_TOTAL_DEMANDAS_TODOS_SISTEMAS_POR_EQUIPE_E_TIPO,
        	query = "select b.nome_sistema as nome, count(*) as total "
        			+ "from tb_tarefa a, tb_sistema b, tb_equipe c "
        			+ "where a.data_abertura between ? and ? and a.sistema_id_sistema = b.id_sistema and a.equipe_id_equipe = c.id_equipe and c.id_equipe = ? and a.tipo_demanda = ? "
        			+ "group by b.nome_sistema",
        	resultSetMapping = Tarefa.MAPPING_TOTAL_DEMANDAS_POR_SISTEMA),
        @NamedNativeQuery(name = Tarefa.NAMED_NATIVE_QUERY_TOTAL_DEMANDAS_TODOS_CLIENTES,
        	query = "select c.nome_cliente as nome, count(*) as total "
        			+ "from tb_tarefa t, tb_cliente c "
        			+ "where t.data_abertura between ? and ? and t.cliente_id_cliente = c.id_cliente "
        			+ "group by c.nome_cliente",
        	resultSetMapping = Tarefa.MAPPING_TOTAL_DEMANDAS_POR_CLIENTE),
        @NamedNativeQuery(name = Tarefa.NAMED_NATIVE_QUERY_TOTAL_DEMANDAS_TODOS_CLIENTES_POR_EQUIPE,
        	query = "select c.nome_cliente as nome, count(*) as total "
        			+ "from tb_tarefa t, tb_cliente c, tb_equipe e "
        			+ "where t.data_abertura between ? and ? and t.cliente_id_cliente = c.id_cliente and t.equipe_id_equipe = e.id_equipe and e.id_equipe = ?"
        			+ "group by c.nome_cliente",
        	resultSetMapping = Tarefa.MAPPING_TOTAL_DEMANDAS_POR_CLIENTE),
        @NamedNativeQuery(name = Tarefa.NAMED_NATIVE_QUERY_TOTAL_DEMANDAS_TODOS_CLIENTES_POR_TIPO,
        	query = "select c.nome_cliente as nome, count(*) as total "
        			+ "from tb_tarefa t, tb_cliente c "
        			+ "where t.data_abertura between ? and ? and t.cliente_id_cliente = c.id_cliente and t.tipo_demanda = ? "
        			+ "group by c.nome_cliente",
        	resultSetMapping = Tarefa.MAPPING_TOTAL_DEMANDAS_POR_CLIENTE),
        @NamedNativeQuery(name = Tarefa.NAMED_NATIVE_QUERY_QUERY_DEMANDAS_TODOS_CLIENTES_POR_EQUIPE_E_TIPO,
        	query = "select c.nome_cliente as nome, count(*) as total "
        			+ "from tb_tarefa t, tb_cliente c, tb_equipe e "
        			+ "where t.data_abertura between ? and ? and t.cliente_id_cliente = c.id_cliente and t.equipe_id_equipe = e.id_equipe and e.id_equipe = ? and t.tipo_demanda = ? "
        			+ "group by c.nome_cliente",
        	resultSetMapping = Tarefa.MAPPING_TOTAL_DEMANDAS_POR_CLIENTE)
})// fim da declaracao de namednativequeries
public class Tarefa implements Serializable {

    // Constantes que servem para guardar os nomes das queries JPQL definidas para entidade Tarefa
    public static final String NAMED_QUERY_BUSCAR_TODOS = "buscarTodasTarefas";
    public static final String NAMED_QUERY_BUSCAR_POR_COLABORADOR = "buscarTodasTarefasPorColaborador";
    public static final String NAMED_QUERY_BUSCAR_POR_COLABORADOR_E_STATUS = "buscarTodosTarefasPorColaboradorEStatus";
    public static final String NAMED_QUERY_BUSCAR_POR_STATUS = "buscarTodasTarefasPorStatus";
    public static final String NAMED_QUERY_BUSCAR_POR_EQUIPE = "buscarTodasTarefasPorEquipe";
    public static final String NAMED_QUERY_BUSCAR_POR_CLIENTE = "buscarTodasTarefasPorCliente";
    public static final String NAMED_QUERY_BUSCAR_POR_SISTEMA = "buscarTodasTarefasPorSistema";
    public static final String NAMED_QUERY_BUSCAR_TODOS_DATAS_DEFINIDAS = "buscarTodasTarefasDatasDefinidas";
    public static final String NAMED_QUERY_BUSCAR_POR_STATUS_DATAS_DEFINIDAS = "buscarPorStatusDatasDefinidas";
    public static final String NAMED_QUERY_BUSCAR_POR_COLABORADOR_E_STATUS_DATAS_DEFINIDAS = "buscarTodasTarefasPorColaboradorEStatusDatasDefinidas";
    public static final String NAMED_QUERY_BUSCAR_POR_EQUIPE_E_DATAS = "buscarTodasTarefasPorColaboradorEDatas";
    public static final String NAMED_QUERY_BUSCAR_POR_CLIENTE_E_DATAS = "buscarTodasTarefasPorClienteEDatas";
    public static final String NAMED_QUERY_BUSCAR_POR_SISTEMA_E_DATAS = "buscarTodasTarefasPorSistemaEDatas";

    // Constantes que servem para guardar os nomes das queries nativas definidas para entidade Tarefa
    public static final String NAMED_NATIVE_QUERY_TOTAL_TODAS_EQUIPES_POR_TIPO = "totalDemandasEquipesPorTipo";
    public static final String NAMED_NATIVE_QUERY_TOTAL_TODAS_EQUIPES = "totalDemandasDeTodasEquipes";
    public static final String NAMED_NATIVE_QUERY_TOTAL_DEMANDAS_TODOS_SISTEMAS = "totalDemandasDeTodosSistema";
    public static final String NAMED_NATIVE_QUERY_TOTAL_DEMANDAS_TODOS_SISTEMAS_POR_EQUIPE = "totalDemandasTodosSistemasPorEquipe";
    public static final String NAMED_NATIVE_QUERY_TOTAL_DEMANDAS_TODOS_SISTEMAS_POR_TIPO = "totalDemandasTodosSistemasPorTipo";
    public static final String NAMED_NATIVE_QUERY_TOTAL_DEMANDAS_TODOS_SISTEMAS_POR_EQUIPE_E_TIPO = "totalDemandasTodosSistemasPorEquipeETipo";
    public static final String NAMED_NATIVE_QUERY_TOTAL_DEMANDAS_TODOS_CLIENTES = "totalDemandasDeTodosClientes";
    public static final String NAMED_NATIVE_QUERY_TOTAL_DEMANDAS_TODOS_CLIENTES_POR_EQUIPE = "totalDemandasTodosClientesPorEquipe";
    public static final String NAMED_NATIVE_QUERY_TOTAL_DEMANDAS_TODOS_CLIENTES_POR_TIPO = "totalDemandasTodosClientesPorTipo";
    public static final String NAMED_NATIVE_QUERY_QUERY_DEMANDAS_TODOS_CLIENTES_POR_EQUIPE_E_TIPO = "totalDemandasTodosClientesPorEquipeETipo";

    // Constantes que servem para guardar os nomes dos mapeamentos definidos para as queries nativas.
    public static final String MAPPING_TOTAL_DEMANDAS_POR_EQUIPE = "totalDemandasPorEquipeMapping";
    public static final String MAPPING_TOTAL_DEMANDAS_POR_SISTEMA = "totalDemandasPorSistemaMapping";
    public static final String MAPPING_TOTAL_DEMANDAS_POR_CLIENTE = "totalDemandasPorClienteMapping";

    private static final long serialVersionUID = 1L;

    private Long id;
    private String numeroDemanda;
    private String titulo;
    private Prioridade prioridade;
    private TipoDemanda tipoDemanda;
    private Date abertura;
    private Date inicio;
    private Date finalPlanejado;
    private Date finalEfetivo;
    private Status status;
    private Integer estimativa;
    private Integer totalHoras;
    private Boolean replanajado;
    private String observacao;
    private Colaborador colaborador = new Colaborador();
    private Cliente cliente = new Cliente();
    private Equipe equipe = new Equipe();
    private Sistema sistema = new Sistema();

    @Version
    private Long versao;

    public Tarefa() {
        super();
    }

    public Tarefa(String numeroDemanda, String titulo, Prioridade prioridade, TipoDemanda tipoDemanda,
                  Date abertura, Date inicio, Date finalPlanejado, Date finalEfetivo,
                  Status status, Integer estimativa, Integer totalHoras, Boolean replanajado,
                  String observacao, Colaborador colaborador, Cliente cliente,
                  Equipe equipe, Sistema sistema) {
        super();
        this.numeroDemanda = numeroDemanda;
        this.titulo = titulo;
        this.prioridade = prioridade;
        this.tipoDemanda = tipoDemanda;
        this.abertura = abertura;
        this.inicio = inicio;
        this.finalPlanejado = finalPlanejado;
        this.finalEfetivo = finalEfetivo;
        this.status = status;
        this.estimativa = estimativa;
        this.totalHoras = totalHoras;
        this.replanajado = replanajado;
        this.observacao = observacao;
        this.colaborador = colaborador;
        this.cliente = cliente;
        this.equipe = equipe;
        this.sistema = sistema;
        this.observacao = observacao;
    } // fim do construtor

    public Tarefa(String numeroDemanda, String titulo, Prioridade prioridade) {
        super();
        this.numeroDemanda = numeroDemanda;
        this.titulo = titulo;
        this.prioridade = prioridade;
        this.colaborador = null;
        this.cliente = null;
        this.equipe = null;
        this.sistema = null;
    } // fim do construtor

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tarefa")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "numero_demanda", unique = true, nullable = false)
    public String getNumeroDemanda() {
        return numeroDemanda;
    }

    public void setNumeroDemanda(String numeroDemanda) {
        this.numeroDemanda = numeroDemanda;
    }

    @Column(name = "titulo_demanda", nullable = false, length = 130)
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "prioridade_demanda", nullable = false, length = 80)
    public Prioridade getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(Prioridade prioridade) {
        this.prioridade = prioridade;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_demanda", nullable = false, length = 80)
    public TipoDemanda getTipoDemanda() {
        return tipoDemanda;
    }

    public void setTipoDemanda(TipoDemanda tipoDemanda) {
        this.tipoDemanda = tipoDemanda;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "data_abertura", nullable = false)
    public Date getAbertura() {
        return abertura;
    }

    public void setAbertura(Date abertura) {
        this.abertura = abertura;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "data_inicio")
    public Date getInicio() {
        return inicio;
    }

    public void setInicio(Date inicio) {
        this.inicio = inicio;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "data_fim_planejado")
    public Date getFinalPlanejado() {
        return finalPlanejado;
    }

    public void setFinalPlanejado(Date finalPlanejado) {
        this.finalPlanejado = finalPlanejado;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "data_fim_efetivo")
    public Date getFinalEfetivo() {
        return finalEfetivo;
    }

    public void setFinalEfetivo(Date finalEfetivo) {
        this.finalEfetivo = finalEfetivo;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "status_tarefa", nullable = false, length = 80)
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Column(name = "estimativa_tarefa")
    public Integer getEstimativa() {
        return estimativa;
    }

    public void setEstimativa(Integer estimativa) {
        this.estimativa = estimativa;
    }

    @Column(name = "total_horas_tarefa")
    public Integer getTotalHoras() {
        return totalHoras;
    }

    public void setTotalHoras(Integer totalHoras) {
        this.totalHoras = totalHoras;
    }

    @Column(name = "replanejado")
    public Boolean getReplanajado() {
        return replanajado;
    }

    public void setReplanajado(Boolean replanajado) {
        this.replanajado = replanajado;
    }

    @Lob
    @Column(name = "observacao_tarefa")
    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    @ManyToOne
    public Colaborador getColaborador() {
        return colaborador;
    }

    public void setColaborador(Colaborador colaborador) {
        this.colaborador = colaborador;
    }

    @ManyToOne
    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    @ManyToOne
    public Equipe getEquipe() {
        return equipe;
    }

    public void setEquipe(Equipe equipe) {
        this.equipe = equipe;
    }

    @ManyToOne
    public Sistema getSistema() {
        return sistema;
    }

    public void setSistema(Sistema sistema) {
        this.sistema = sistema;
    }

} // fim da classe Tarefa