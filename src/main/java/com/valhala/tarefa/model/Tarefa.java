package com.valhala.tarefa.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

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
        @NamedQuery(name = Tarefa.NAMED_QUERY_BUSCAR_TODOS, query = "select t from Tarefa as t"),
        @NamedQuery(name = Tarefa.NAMED_QUERY_BUSCAR_POR_COLABORADOR, query = "select t from Tarefa as t where t.colaborador = :colaborador"),
        @NamedQuery(name = Tarefa.NAMED_QUERY_BUSCAR_POR_COLABORADOR_E_STATUS, query = "select t from Tarefa as t where t.colaborador = :colaborador and t.status in (:status)"),
        @NamedQuery(name = Tarefa.NAMED_QUERY_BUSCAR_POR_STATUS, query = "select t from Tarefa as t where t.status in (:status)"),
        @NamedQuery(name = Tarefa.NAMED_QUERY_BUSCAR_POR_EQUIPE, query = "select t from Tarefa as t where t.equipe = :equipe"),
        @NamedQuery(name = Tarefa.NAMED_QUERY_BUSCAR_POR_CLIENTE, query = "select t from Tarefa as t where t.cliente = :cliente"),
        @NamedQuery(name = Tarefa.NAMED_QUERY_BUSCAR_POR_SISTEMA, query = "select t from Tarefa as t where t.sistema = :sistema"),
        @NamedQuery(name = Tarefa.NAMED_QUERY_BUSCAR_POR_STATUS_DATAS_DEFINIDAS, query = "select t from Tarefa as t where t.status in (:status) and t.inicio is not null and t.finalPlanejado is not null"),
        @NamedQuery(name = Tarefa.NAMED_QUERY_BUSCAR_TODOS_DATAS_DEFINIDAS, query = "select t from Tarefa as t where t.inicio is not null and t.finalPlanejado is not null"),
        @NamedQuery(name = Tarefa.NAMED_QUERY_BUSCAR_POR_COLABORADOR_E_STATUS_DATAS_DEFINIDAS, query = "select t from Tarefa as t where t.colaborador = :colaborador and t.status in (:status) and t.inicio is not null and t.finalPlanejado is not null"),
        @NamedQuery(name = Tarefa.NAMED_QUERY_BUSCAR_POR_EQUIPE_E_DATAS, query = "select t from Tarefa as t where t.equipe = :equipe and t.abertura between :dataInicio and :dataFim"),
        @NamedQuery(name = Tarefa.NAMED_QUERY_BUSCAR_POR_CLIENTE_E_DATAS, query = "select t from Tarefa as  t where t.cliente = :cliente and t.abertura between :dataInicio and :dataFim"),
        @NamedQuery(name = Tarefa.NAMED_QUERY_BUSCAR_POR_SISTEMA_E_DATAS, query = "select t from Tarefa as t where t.sistema = :sistema and t.abertura between :dataInicio and :dataFim")
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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tarefa")
    private Long id;
    @Column(name = "numero_demanda", unique = true, nullable = false)
    private Long numeroDemanda;
    @Column(name = "titulo_demanda", nullable = false, length = 130)
    private String titulo;
    @Enumerated(EnumType.STRING)
    @Column(name = "prioridade_demanda", nullable = false, length = 80)
    private Prioridade prioridade;
    @Column(name = "categoria_demanda", nullable = true, length = 200)
    private String categoria;
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_demanda", nullable = false, length = 80)
    private TipoDemanda tipoDemanda;
    @Temporal(TemporalType.DATE)
    @Column(name = "data_abertura", nullable = false, updatable = false)
    private Date abertura;
    @Temporal(TemporalType.DATE)
    @Column(name = "data_inicio")
    private Date inicio;
    @Temporal(TemporalType.DATE)
    @Column(name = "data_desenvolvimento")
    private Date desenvolvimento;
    @Temporal(TemporalType.DATE)
    @Column(name = "data_homologacao")
    private Date homologacao;
    @Temporal(TemporalType.DATE)
    @Column(name = "data_fim_planejado")
    private Date finalPlanejado;
    @Temporal(TemporalType.DATE)
    @Column(name = "data_fim_efetivo")
    private Date finalEfetivo;
    @Enumerated(EnumType.STRING)
    @Column(name = "status_tarefa", nullable = false, length = 80)
    private Status status;	
    @Enumerated(EnumType.STRING)
    @Column(name = "status_sla", nullable = false, length = 50)
    private StatusSla statusSla;
    @Lob
    @Column(name = "observacao_tarefa")
    private String observacao;
    @ManyToOne
    private Colaborador colaborador = new Colaborador();
    @ManyToOne
    private Cliente cliente = new Cliente();
    @ManyToOne
    private Equipe equipe = new Equipe();
    @ManyToOne
    private Sistema sistema = new Sistema();

    @Version
    private Long versao;

    Tarefa() {
        super();
    }

    public Tarefa(Builder builder) {
    	super();
    	this.id = builder.id;
    	this.numeroDemanda = builder.numeroDemanda;
    	this.titulo = builder.titulo;
    	this.prioridade = builder.prioridade;
    	this.tipoDemanda = builder.tipoDemanda;
    	this.categoria = builder.categoria;
    	this.abertura = builder.abertura;
    	this.inicio = builder.inicio;
    	this.desenvolvimento = builder.desenvolvimento;
    	this.homologacao = builder.homologacao;
    	this.finalPlanejado = builder.finalPlanejado;
    	this.finalEfetivo = builder.finalEfetivo;
    	this.status = builder.status;
    	this.statusSla = builder.statusSla;
    	this.observacao = builder.observacao;
    	this.colaborador = builder.colaborador;
    	this.cliente = builder.cliente;
    	this.equipe = builder.equipe;
    	this.sistema = builder.sistema;
	}
    
    public Long getId() {
        return id;
    }
    
    public Long getNumeroDemanda() {
        return numeroDemanda;
    }
    
    public String getCategoria() {
		return categoria;
	}

    public String getTitulo() {
        return titulo;
    }

    public Prioridade getPrioridade() {
        return prioridade;
    }

    public TipoDemanda getTipoDemanda() {
        return tipoDemanda;
    }

    public Date getAbertura() {
        return abertura == null ? null : new Date(abertura.getTime());
    }

    public Date getInicio() {
        return inicio == null ? null : new Date(inicio.getTime());
    }
    
    public Date getDesenvolvimento() {
		return desenvolvimento == null ? null : new Date(desenvolvimento.getTime());
	}
    
    public Date getHomologacao() {
		return homologacao == null ? null : new Date(homologacao.getTime());
	}

    public Date getFinalPlanejado() {
        return finalPlanejado == null ? null : new Date(finalPlanejado.getTime());
    }

    public Date getFinalEfetivo() {
        return finalEfetivo == null ? null : new Date(finalEfetivo.getTime());
    }

    public Status getStatus() {
        return status;
    }
    
    public StatusSla getStatusSla() {
		return statusSla;
	}
    
    public String getObservacao() {
        return observacao;
    }

    public Colaborador getColaborador() {
        return colaborador;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Equipe getEquipe() {
        return equipe;
    }

    public Sistema getSistema() {
        return sistema;
    }
    
    public static class Builder {
    	private Long id = null;
        private Long numeroDemanda;
        private String titulo;
        private Prioridade prioridade;
        private TipoDemanda tipoDemanda;
        private String categoria = "";
        private Date abertura = new Date();
        private Date inicio = null;
        private Date desenvolvimento = null;
        private Date homologacao = null;
        private Date finalPlanejado = null;
        private Date finalEfetivo = null;
        private Status status = Status.ABERTO;	
        private StatusSla statusSla = StatusSla.NAO_VIOLADO;
        private String observacao = "";
        private Colaborador colaborador = new Colaborador();
        private Cliente cliente = new Cliente();
        private Equipe equipe = new Equipe();
        private Sistema sistema = new Sistema();
        
        public Builder(final Long numeroDemanda, final String titulo, final Prioridade prioridade, final TipoDemanda demanda) {
        	this.numeroDemanda = numeroDemanda;
        	this.titulo = titulo;
        	this.prioridade = prioridade;
        	this.tipoDemanda = demanda;
        }
        
        public Builder id(final Long id) {
        	this.id = id;
        	return this;
        }
        
        public Builder categoria(final String categoria) {
        	this.categoria = categoria;
        	return this;
        }
        
        public Builder abertura(final Date abertura) {
        	this.abertura = abertura;
        	return this;
        }
        
        public Builder inicio(final Date inicio) {
        	this.inicio = inicio;
        	return this;
        }
        
        public Builder desenvolvimento(final Date desenvolvimento) {
        	this.desenvolvimento = desenvolvimento;
        	return this;
        }
        
        public Builder homologacao(final Date homologacao) {
        	this.homologacao = homologacao;
        	return this;
        }
        
        public Builder finalPlanejado(final Date finalPlanejado) {
        	this.finalPlanejado = finalPlanejado;
        	return this;
        }
        
        public Builder finalEfetivo(final Date finalEfetivo) {
        	this.finalEfetivo = finalEfetivo;
        	return this;
        }
        
        public Builder status(final Status status) {
        	this.status = status;
        	return this;
        }
        
        public Builder statusSla(final StatusSla statusSla) {
        	this.statusSla = statusSla;
        	return this;
        }
        
        public Builder observacao(final String observacao) {
        	this.observacao = observacao;
        	return this;
        }
        
        public Builder colaborador(final Colaborador colaborador) {
        	this.colaborador = colaborador;
        	return this;
        }
        
        public Builder cliente(final Cliente cliente) {
        	this.cliente = cliente;
        	return this;
        }
        
        public Builder equipe(final Equipe equipe) {
        	this.equipe = equipe;
        	return this;
        }
        
        public Builder sistema(final Sistema sistema) {
        	this.sistema = sistema;
        	return this;
        }
        
        public Tarefa build() {
        	return new Tarefa(this);
        }
        
    }
    
    @Override
    public boolean equals(Object obj) {
    	if (obj == this) {
			return true;
		}
    	if (!(obj instanceof Tarefa)) {
			return false;
		}
    	Tarefa tarefa = (Tarefa) obj;
    	return tarefa.getNumeroDemanda().equals(this.getNumeroDemanda()) && tarefa.getTipoDemanda().equals(this.getTipoDemanda());
    }
    
    @Override
    public int hashCode() {
    	int result = 21;
    	result = 31 * result + (this.getNumeroDemanda() == null ? 0 : this.getNumeroDemanda().hashCode());
    	result = 31 * result + (this.getTipoDemanda() == null ? 0 : this.getTipoDemanda().hashCode());
    	return result;
    }
    
    @Override
    public String toString() {
    	return this.getTipoDemanda() + " - Numero: " + this.getNumeroDemanda();
    }
	
	public void setId(Long id) {
		this.id = id;
	}

	public void setNumeroDemanda(Long numeroDemanda) {
		this.numeroDemanda = numeroDemanda;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public void setPrioridade(Prioridade prioridade) {
		this.prioridade = prioridade;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public void setTipoDemanda(TipoDemanda tipoDemanda) {
		this.tipoDemanda = tipoDemanda;
	}

	public void setAbertura(Date abertura) {
		this.abertura = abertura;
	}

	public void setInicio(Date inicio) {
		this.inicio = inicio;
	}

	public void setDesenvolvimento(Date desenvolvimento) {
		this.desenvolvimento = desenvolvimento;
	}

	public void setHomologacao(Date homologacao) {
		this.homologacao = homologacao;
	}

	public void setFinalPlanejado(Date finalPlanejado) {
		this.finalPlanejado = finalPlanejado;
	}

	public void setFinalEfetivo(Date finalEfetivo) {
		this.finalEfetivo = finalEfetivo;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public void setStatusSla(StatusSla statusSla) {
		this.statusSla = statusSla;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public void setColaborador(Colaborador colaborador) {
		this.colaborador = colaborador;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public void setEquipe(Equipe equipe) {
		this.equipe = equipe;
	}

	public void setSistema(Sistema sistema) {
		this.sistema = sistema;
	}

	public Long retornarQuantidadeDeDiasAberto() {
		Date hoje = new Date();
		long diferencaDatas = (hoje.getTime() - this.getAbertura().getTime()) + 3600000;
		long diferencaDias = diferencaDatas / 86400000L;
		return Long.valueOf(diferencaDias);
	}
    
} // fim da classe Tarefa